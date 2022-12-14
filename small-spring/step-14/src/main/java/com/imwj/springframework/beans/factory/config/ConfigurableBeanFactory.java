package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.factory.HierarchicalBeanFactory;
import com.imwj.springframework.utils.StringValueResolver;

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


    /**
     * 销毁单例对象
     */
    void destroySingletons();

    /**
     * 添加占位符解析器
     * @param valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 处理占位符的值
     * @param value
     * @return
     */
    String resolveEmbeddedValue(String value);
}
