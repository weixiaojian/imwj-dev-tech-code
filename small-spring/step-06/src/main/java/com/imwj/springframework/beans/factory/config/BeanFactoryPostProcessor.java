package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author wj
 * @create 2022-11-04 15:37
 */
public interface BeanFactoryPostProcessor {


    /**
     * 在所有的BeanDefinition加载完成后，实例化bena对象之前，提供修改BeanDefinition属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)throws BeansException;

}
