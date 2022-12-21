package com.imwj.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * 限定bean属性注入时只有一个
 * @author wj
 * @create 2022-12-12 17:38
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";

}
