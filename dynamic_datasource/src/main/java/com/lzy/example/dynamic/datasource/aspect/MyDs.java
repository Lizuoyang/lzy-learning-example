package com.lzy.example.dynamic.datasource.aspect;

import java.lang.annotation.*;

/**
 * 动态数据源注解
 * @author lizuoyang
 * @date 2023/09/14
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyDs {
    String value() default "master";
}
