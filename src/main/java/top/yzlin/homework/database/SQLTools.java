package top.yzlin.homework.database;

import top.yzlin.homework.database.annotation.DBField;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * jpa啊，mybatis都是好东西
 * 于是我自己搞了一个差不多的自动封装
 *
 * 在第二次试验中因为hibernate能用了所以这个类作废
 */
@Deprecated
public class SQLTools {
    private final Map<Class,Map<String,Method>> classFieldMap=new HashMap<>();


    public <T> T resultSetToObject(Class<T> tClass,ResultSet resultSet) throws SQLException {
        try {
            return resultSetToObject(tClass.newInstance(),resultSet);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用来讲数据库的查询返回结果封装成javabean
     * @param t 即将被注入的对应的实例
     * @param resultSet 查询结果，只操作当前行
     * @param <T> 任意类型，任意实体类
     * @return 返回t本身，这个返回是可忽略的
     * @throws SQLException 不知道会有什么问题
     */
    public <T> T resultSetToObject(T t,ResultSet resultSet) throws SQLException {
        Class tClazz=t.getClass();
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
        String[] names=new String[resultSetMetaData.getColumnCount()];
        for (int i = 1 , j=names.length; i <= j; i++) {
            names[i-1]=resultSetMetaData.getColumnName(i);
        }
        Map<String,Method> methodMap = getClassFieldMap(tClazz);
        for (String name : names) {
            Method m=methodMap.get(name);
            if(m!=null){
                try {
                    Parameter[] parameters=m.getParameters();
                    if(parameters.length==1){
                        m.invoke(t,typeCast(resultSet,name,parameters[0].getType()));
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }


    /**
     * 有些类如果解析过了，就将解析结果保存起来，免得多次反射
     * @param clazz 获取类的解析结果
     * @return 返回的map，key是对应这个类的属性，value是对应set方法
     */
    private Map<String,Method> getClassFieldMap(Class clazz){
        if(classFieldMap.containsKey(clazz)){
            return classFieldMap.get(clazz);
        }else{
            Map<String,Method> methodMap= Stream.of(clazz.getMethods())
                    .filter(c-> c.getAnnotation(DBField.class) != null || (c.getName().indexOf("set") == 0  && c.getName().length()>3))
                    .collect(Collectors.toMap(k->{
                        DBField dbField=k.getAnnotation(DBField.class);
                        if(dbField!=null){
                            return dbField.value();
                        }else{
                            String name= k.getName();
                            return (char)(name.charAt(3)|32)+ name.substring(4);
                        }
                    },v-> v));
            classFieldMap.put(clazz,methodMap);
            return methodMap;
        }
    }

    /**
     * 基本类型转换，就很烦
     * @param resultSet 数据库查询结果
     * @param name 字段
     * @param c 类型
     * @return 转换后的结果
     * @throws SQLException 不知道会怎么崩
     */
    private Object typeCast(ResultSet resultSet,String name, Class c) throws SQLException {
        if(String.class.equals(c)){
            return resultSet.getString(name);
        }else if (int.class.equals(c) || Integer.class.equals(c)) {
            return resultSet.getInt(name);
        } else if (double.class.equals(c) || Double.class.equals(c)) {
            return resultSet.getDouble(name);
        } else if (float.class.equals(c) || Float.class.equals(c)) {
            return resultSet.getFloat(name);
        } else if (long.class.equals(c) || Long.class.equals(c)) {
            return resultSet.getLong(name);
        } else if (short.class.equals(c) || Short.class.equals(c)) {
            return resultSet.getShort(name);
        } else if (boolean.class.equals(c) || Boolean.class.equals(c)) {
            return resultSet.getBoolean(name);
        } else if (Date.class.equals(c)) {
            return resultSet.getDate(name);
        } else {
            return null;
        }
    }
}
