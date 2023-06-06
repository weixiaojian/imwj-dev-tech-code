package com.imwj.design.agent;

import java.lang.annotation.*;

/**
 * 模拟mybatis的@select注解
 * @author wj
 * @create 2023-06-06 15:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Select {

    /**
     * sql语句
     * @return
     */
    String value() default "";
}
