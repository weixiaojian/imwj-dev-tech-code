package com.imwj.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * Bean的作用域 默认单例
 * @author wj
 * @create 2022-12-09 17:21
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {


    String value() default "singleton";
}
