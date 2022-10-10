package com.imwj.springframework.factory.support;

import com.imwj.springframework.factory.config.BeanDefinition;

/**
 * BeanDefinition注册
 * @author wj
 * @create 2022-10-09 17:39
 */
public interface BeanDefinitionRegistry {

    /**
     * BeanDefinition注册
     * @param beanName
     * @param beanDefinition
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
