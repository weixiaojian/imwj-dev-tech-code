package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author wj
 * @create 2022-11-01 17:22
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
