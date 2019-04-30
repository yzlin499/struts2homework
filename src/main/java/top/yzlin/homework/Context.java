package top.yzlin.homework;

import top.yzlin.homework.ioc.ComponentInit;
import top.yzlin.homework.ioc.Value;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 一个具有基本ioc功能的简单上下文类
 */
public class Context {
    static {
        try {
            URL url;
            url = Thread.currentThread().getContextClassLoader().getResource("/");
            if (url == null) {
                url = Class.class.getResource("/");
            }
            CLASSPATH = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static File CLASSPATH;
    private Map<Class, Object> classObjectMap = new HashMap<>();
    private Properties properties = new Properties();
    private static final Context INSTANCE = new Context();

    public static Context getInstance() {
        return INSTANCE;
    }


    private Context() {
        try {
            properties.load(new FileReader(getResources("config.properties")));
        } catch (IOException ignored) {
        }
        addComponent(Context.class, this);
        for (Class component : ContextConfig.COMPONENTS) {
            addComponent(component);
        }
    }

    public <T> void addComponent(Class<T> tClass, T object) {
        classObjectMap.put(tClass, object);
    }

    public <T> void addComponent(T object) {
        addComponent((Class<T>) object.getClass(), object);
    }

    public <T> void addComponent(Class<T> tClass) {
        try {
            T t = tClass.newInstance();
            initComponent(t);
            addComponent(tClass, t);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(tClass.getName() + "实例化失败");
            e.printStackTrace();
        }
    }

    public <T> T getComponent(Class<T> tClass) {
        if (classObjectMap.containsKey(tClass)) {
            return (T) classObjectMap.get(tClass);
        } else {
            for (Object o : classObjectMap.values()) {
                if (tClass.isInstance(o)) {
                    return (T) o;
                }
            }
        }
        return null;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 对基本生命周期维护
     * @param o 对于组件的初始化
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void initComponent(Object o) throws InvocationTargetException, IllegalAccessException {
        Class clazz = o.getClass();
        for (Method method : clazz.getMethods()) {
            Value temp = method.getAnnotation(Value.class);
                if (method.getAnnotation(Value.class) != null) {
                    valueAnnotationDeal(method, o, temp);
                } else if (method.getAnnotation(Resource.class) != null) {
                    resourceAnnotationDeal(method, o);
                }
        }
        if (o instanceof ComponentInit) {
            ((ComponentInit) o).init();
        }
    }

    /**
     * 如果方法带有resource注解
     *
     * @param method 方法
     * @param o      对应对象
     */
    private void resourceAnnotationDeal(Method method, Object o) throws InvocationTargetException, IllegalAccessException {
        Parameter[] parameters = method.getParameters();
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            params[i] = getComponent(parameters[i].getType());
        }
        method.invoke(o, params);
    }

    /**
     * 如果方法带有value注解
     *
     * @param method 方法
     * @param o      对应对象
     * @param value  注解本身对象
     */
    private void valueAnnotationDeal(Method method, Object o, Value value) throws InvocationTargetException, IllegalAccessException {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 1) {
            Class c = parameters[0].getType();
            String v = getProperty(value.value());
            Object param = String.class.equals(c) ? v : typeCast(v, c);
            if (param != null) {
                method.invoke(o, param);
            }
        }
    }

    /**
     * 获取类目录底下的文件
     *
     * @param path 位置
     * @return 对应文件
     */
    public static File getResources(String path) {
        return new File(CLASSPATH, path);
    }

    /**
     * 用来对基本类型的转换
     *
     * @param str 字符串
     * @param c   数据类型
     * @return 相对应的封装类型
     */
    private Object typeCast(String str, Class c) {
        if (int.class.equals(c) || Integer.class.equals(c)) {
            return Integer.valueOf(str);
        } else if (double.class.equals(c) || Double.class.equals(c)) {
            return Double.valueOf(str);
        } else if (float.class.equals(c) || Float.class.equals(c)) {
            return Float.valueOf(str);
        } else if (long.class.equals(c) || Long.class.equals(c)) {
            return Long.valueOf(str);
        } else if (short.class.equals(c) || Short.class.equals(c)) {
            return Short.valueOf(str);
        } else if (boolean.class.equals(c) || Boolean.class.equals(c)) {
            return Boolean.valueOf(str);
        } else {
            return null;
        }
    }

}
