package com.imwj.springframework.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bean属性注入
 * @author wj
 * @create 2022-12-12 17:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
public @interface Autowired {
}
