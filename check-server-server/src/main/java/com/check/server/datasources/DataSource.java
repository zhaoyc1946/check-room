package com.check.server.datasources;

import java.lang.annotation.*;

/**
 * @description: 多数据源注解
 * @author: Mr.ZHAO
 * @cereate: 2018/07/06 22:10:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}