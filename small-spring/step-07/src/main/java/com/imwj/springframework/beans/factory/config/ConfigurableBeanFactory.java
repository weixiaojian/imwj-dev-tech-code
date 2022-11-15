package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * 可配置的BeanFactory
 * @author wj
 * @create 2022-11-01 17:22
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 将BeanPostProcessors添加到集合中
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
