package com.imwj.springframework.factory.config;

/**
 * 单例Bnea注册接口
 * @author wj
 * @create 2022-10-09 17:36
 */
public interface SingletonBeanRegistry {

    /**
     * 根据beanName获取单例对象
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

}
