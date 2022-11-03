package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.config.BeanDefinition;

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

    boolean containsBeanDefinition(String beanName) throws BeansException;
}
