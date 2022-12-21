package com.imwj.springframework.aop;

/**
 * 类匹配类
 * @author wj
 * @create 2022-11-30 17:27
 */
public interface ClassFilter {


    /**
     * 切入点是否应该应用于给定的接口或目标类
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
