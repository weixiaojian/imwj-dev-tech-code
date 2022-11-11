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

}
