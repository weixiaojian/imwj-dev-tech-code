package com.imwj.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 * @author wj
 * @create 2022-11-28 17:40
 */
public interface MethodMatcher {

    /**
     * 匹配方法是否在目标类中
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);

}
