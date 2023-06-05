package com.imwj.design.door.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拦截注解
 * @author wj
 * @create 2023-06-05 16:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoDoor {

    /**
     * 拦截字段值
     * @return
     */
    String key() default "";

    /**
     * 拦截时返回json
     * @return
     */
    String returnJson() default "";

}