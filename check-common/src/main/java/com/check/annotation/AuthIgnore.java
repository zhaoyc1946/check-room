package com.check.annotation;

import java.lang.annotation.*;

/**
 * @description: API接口忽略token验证
 * @author: Mr.ZHAO
 * @cereate: 2018/07/17 22:39:18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {
}
