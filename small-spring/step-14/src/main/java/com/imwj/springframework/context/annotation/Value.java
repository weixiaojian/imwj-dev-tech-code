package com.imwj.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * value属性注入
 * @author wj
 * @create 2022-12-12 17:38
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    String value();
}
