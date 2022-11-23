package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略
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
