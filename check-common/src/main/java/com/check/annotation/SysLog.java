package com.check.annotation;

import java.lang.annotation.*;

/**
 * @description: 日志注解
 * @author: Mr.ZHAO
 * @cereate: 2018/07/01 17:16:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
