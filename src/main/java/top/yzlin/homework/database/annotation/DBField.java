package top.yzlin.homework.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 有些类，属性与数据库的表头，是对不上的
 * 就像在其他持久化框架啊
 * 都有类似的注解
 * 就是打个注解，就可以和数据库的表头对上了，方便持久化
 * 所以说就有了这个类
 *
 * 直接作废
 */
@Deprecated
@Target({METHOD})
@Retention(RUNTIME)
public @interface DBField {
    String value();
}
