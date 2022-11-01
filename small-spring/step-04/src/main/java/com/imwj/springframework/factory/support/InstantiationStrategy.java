package com.imwj.springframework.factory.support;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author wj
 * @create 2022-10-11 16:51
 */
public interface InstantiationStrategy {

    /**
     * 实例化策略
     * @param beanDefinition
     * @param beanName
     * @param ctor
     * @param args
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
