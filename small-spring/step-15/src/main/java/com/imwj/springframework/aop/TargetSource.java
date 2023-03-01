package com.imwj.springframework.aop;

import com.imwj.springframework.utils.ClassUtils;

/**
 * 被代理的目标对象
 * @author wj
 * @create 2022-12-01 15:31
 */
public class TargetSource {

    private final Object target;

    /**
     * 初始化构造 传入被代理的目标对象
     * @param target
     */
    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取被代理的目标对象的class
     * 如果市cglib代理对象需要通过getSuperclass获取
     * @return
     */
    public Class<?>[] getTargetClass(){
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    /**
     * 或被代理的目标对象
      * @return
     */
    public Object getTarget(){
        return this.target;
    }

}
