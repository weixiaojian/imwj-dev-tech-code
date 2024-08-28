package com.imwj.middleware.db.router.config;

import java.lang.annotation.*;

/**
 * @author wj
 * @create 2024-08-28 14:39
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouterStrategy {

    boolean splitTable() default false;

}