package com.imwj.middleware.db.router.annotation;

import java.lang.annotation.*;

/**
 * @author wj
 * @create 2024-08-07 17:22
 * @description 自定义路由注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {

    String key() default "";

}