package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.BeansException;

/**
 * 实例化感知Bean PostProcessor
 * @author wj
 * @create 2022-12-07 15:45
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;


}
