package com.imwj.springframework.factory.config;

/**
 * @author wj
 * @create 2022-10-11 16:38
 */
public interface SingletonBeanRegistry {

    /**
     * 根据beanName获取单例对象
     * @return
     */
    Object getSingleton(String beanName);

}
