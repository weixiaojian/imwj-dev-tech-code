package com.imwj.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 自定义@JDBCConfig注解
 * @author wj
 * @create 2023-04-27 14:51
 */
@Target({METHOD,TYPE}) // 表示这个注解可以用用在类/接口上，还可以用在方法上
@Retention(RetentionPolicy.RUNTIME) // 示这是一个运行时注解
@Inherited // 表示这个注解可以被子类继承
@Documented // 表示当执行javadoc的时候，本注解会生成相关文档
public @interface JDBCConfig {

    /**
     * 定义了一些注解的参数
     */
    String ip();

    int port() default 3306;

    String database();

    String encoding();

    String loginName();

    String password();
}
