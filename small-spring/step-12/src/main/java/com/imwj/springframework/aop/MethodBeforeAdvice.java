package com.imwj.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法before处理
 * @author wj
 * @create 2022-12-07 10:50
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

    /**
     * 在指定方法之前调用
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target)throws Throwable;

}
