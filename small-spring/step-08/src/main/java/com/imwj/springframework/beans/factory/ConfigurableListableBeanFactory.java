package com.imwj.springframework.beans.factory;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.config.BeanPostProcessor;
import com.imwj.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 可配置的beanFactory列表
 * @author wj
 * @create 2022-11-01 17:23
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 根据beanName获取到beanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例bean
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;

    /**
     * 将BeanPostProcessors添加到集合中
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
