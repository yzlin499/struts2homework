package top.yzlin.homework.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface QueryParam {
    String value();
}
