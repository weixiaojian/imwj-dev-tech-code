package com.imwj.springframework.context.stereotype;

import java.lang.annotation.*;

/**
 * 标识bean交由spring处理
 * @author wj
 * @create 2022-12-09 17:22
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {


    String value() default "";

}
