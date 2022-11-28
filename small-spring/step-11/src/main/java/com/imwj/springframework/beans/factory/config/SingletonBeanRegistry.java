package com.imwj.springframework.beans.factory.config;

/**
 * 单例bean注册器
 * @author wj
 * @create 2022-10-11 16:38
 */
public interface SingletonBeanRegistry {

    /**
     * 根据beanName获取单例对象
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例bean对象
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

}
