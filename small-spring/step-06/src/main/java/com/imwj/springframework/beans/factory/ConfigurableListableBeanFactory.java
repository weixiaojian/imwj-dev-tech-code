package com.imwj.springframework.beans.factory;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.config.BeanPostProcessor;
import com.imwj.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author wj
 * @create 2022-11-01 17:23
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
