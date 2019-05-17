package top.yzlin.homework.database;


import com.sun.media.jfxmediaimpl.platform.Platform;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import top.yzlin.homework.database.annotation.*;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class DAOInvocationHandler implements InvocationHandler {
    private Map<String, Function<Object[], Object>> methodMap = new HashMap<>();
    private SessionFactory sessionFactory;

    public DAOInvocationHandler(Class<?> daoClass, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        for (Method method : daoClass.getMethods()) {
            if (!method.isDefault()) {
                Annotation annotation;
                if (method.getAnnotation(Save.class) != null) {
                    makeSave(method);
                } else if ((annotation = method.getAnnotation(Select.class)) != null) {
                    makeSelect((Select) annotation, method);
                } else if ((annotation = method.getAnnotation(Delete.class)) != null) {
                    makeDelete((Delete) annotation, method);
                } else if (method.getAnnotation(SaveOrUpdate.class) != null) {
                    makeSaveOrUpdate(method);
                } else if (method.getAnnotation(GetById.class) != null) {
                    makeGetById(method);
                } else {
                    methodMap.put(method.getName(), p -> null);
                }
            }
        }
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    private void makeGetById(Method method) {
        Class<?> returnType = method.getReturnType();
        methodMap.put(method.getName(), p -> {
            if (p.length > 0 && p[0] instanceof Serializable) {
                return currentSession().get(returnType, (Serializable) p[0]);
            } else {
                return null;
            }
        });
    }

    private void makeSaveOrUpdate(Method method) {
        Supplier<Object> supplier = createVoidReturnFunc(method);
        methodMap.put(method.getName(), p -> {
            currentSession().saveOrUpdate(p[0]);
            return supplier.get();
        });
    }

    private void makeSelect(Select select, Method method) {
        String hql = select.value();
        Function<Query, Object> returnFunc;
        Class<?> returnType = method.getReturnType();
        if (List.class.equals(returnType)) {
            returnFunc = Query::list;
        } else if (returnType.isArray()) {
            returnFunc = q -> {
                List list = q.list();
                Object o = Array.newInstance(returnType.getComponentType(), list.size());
                for (int i = 0; i < list.size(); i++) {
                    Array.set(o, i, list.get(i));
                }
                return o;
            };
        } else {
            returnFunc = q -> q.setMaxResults(1).uniqueResult();
        }
        String[] params = getMethodParams(method);
        methodMap.put(method.getName(), p -> returnFunc.apply(operateQuery(hql, params, p, currentSession())));
    }

    /**
     * delect操作比较复杂，如果delect注解为没有写hql语句，则用delect方法进行删除，如果有，则运行hql语句
     *
     * @param delete
     * @param method
     */
    private void makeDelete(Delete delete, Method method) {
        if ("".equals(delete.value())) {
            Supplier<Object> voidReturnFunc = createVoidReturnFunc(method);
            methodMap.put(method.getName(), p -> {
                currentSession().delete(p[0]);
                return voidReturnFunc.get();
            });
        } else {
            String hql = delete.value();
            Function<Integer, Object> returnFunc;
            Class<?> returnType = method.getReturnType();
            if (boolean.class.equals(returnType) || Boolean.class.equals(returnType)) {
                returnFunc = s -> s > 0;
            } else if (int.class.equals(returnType) || Integer.class.equals(returnType)) {
                returnFunc = s -> s;
            } else {
                returnFunc = s -> null;
            }
            String[] params = getMethodParams(method);
            methodMap.put(method.getName(), p -> returnFunc.apply(operateQuery(hql, params, p, currentSession()).executeUpdate()));
        }
    }

    /**
     * 保存
     *
     * @param method
     */
    private void makeSave(Method method) {
        Function<Serializable, Object> returnFunc;
        Class<?> returnType = method.getReturnType();
        if (boolean.class.equals(returnType) || Boolean.class.equals(returnType)) {
            returnFunc = s -> Integer.parseInt(s.toString()) > 0;
        } else if (int.class.equals(returnType) || Integer.class.equals(returnType)) {
            returnFunc = s -> Integer.parseInt(s.toString());
        } else {
            returnFunc = s -> null;
        }
        methodMap.put(method.getName(), p -> returnFunc.apply(currentSession().save(p[0])));
    }

    /**
     * idea因为我代码重复了，非要我分离提取代码
     *
     * @param hql
     * @param params
     * @param p
     * @param s
     * @return
     */
    private Query operateQuery(String hql, String[] params, Object[] p, Session s) {
        Query query = s.createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            if (p[i] instanceof OperateQuery) {
                ((OperateQuery) p[i]).apply(query);
            } else if (params[i] == null) {
                query.setParameter(i + 1, p[i]);
            } else {

                query.setParameter(params[i], p[i]);
            }
        }
        return query;
    }

    /**
     * idea因为我代码重复了，非要我分离提取代码
     *
     * @param method
     * @return
     */
    private String[] getMethodParams(Method method) {
        Parameter[] parameters = method.getParameters();
        String[] params = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            QueryParam queryParam = parameters[i].getAnnotation(QueryParam.class);
            params[i] = queryParam == null ? null : queryParam.value();
        }
        return params;
    }


    /**
     * 生成一个啥都不返回的方法
     * 为了代码精简
     *
     * @param method
     * @return
     */
    private Supplier<Object> createVoidReturnFunc(Method method) {
        Supplier<Object> supplier;
        Class<?> returnType = method.getReturnType();
        if (boolean.class.equals(returnType) || Boolean.class.equals(returnType)) {
            supplier = () -> true;
        } else if (int.class.equals(returnType) || Integer.class.equals(returnType)) {
            supplier = () -> 0;
        } else {
            supplier = () -> null;
        }
        return supplier;
    }

    /**
     * 动态代理的核心操作
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else if (method.isDefault()) {
            //默认接口的实现
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
            constructor.setAccessible(true);

            Class<?> declaringClass = method.getDeclaringClass();

            return constructor.newInstance(declaringClass,
                    MethodHandles.Lookup.PUBLIC |
                            MethodHandles.Lookup.PRIVATE |
                            MethodHandles.Lookup.PROTECTED |
                            MethodHandles.Lookup.PACKAGE)
                    .unreflectSpecial(method, declaringClass)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
            //java8的默认接口的代理是真的难，因为要分页于是想到了这个操作，但是发现我不会啊，基本没得玩啊，相关资料又少的可怜
            //引用作者一句话：
            //-----------------
            //虽然解决了问题，但还是觉得java 8应该在method里添加针对interface default方法的直接调用方式，这样绕一大圈的解决方式显然不够优雅。
            //只能期待reflect api下个版本的修改了。
            //---------------------
            //作者：weixin_34268310
            //来源：CSDN
            //原文：https://blog.csdn.net/weixin_34268310/article/details/87374870
            //版权声明：本文为博主原创文章，转载请附上博文链接！
        } else {
            return methodMap.containsKey(method.getName()) ? methodMap.get(method.getName()).apply(args) : null;
        }
        return null;
    }
}
