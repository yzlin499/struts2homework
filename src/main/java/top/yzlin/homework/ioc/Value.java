package top.yzlin.homework.ioc;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 在j2ee里面找不到类似于spring的Value的注解，于是选择了自己搞一个，用于属性值的注入
 * 注意这个注解与Resource互斥且优先级高于Resource
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface Value {
    String value();
}
