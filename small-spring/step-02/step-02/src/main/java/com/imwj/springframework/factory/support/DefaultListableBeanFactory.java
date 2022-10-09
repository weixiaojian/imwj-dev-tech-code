package com.imwj.springframework.factory.support;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * bean的核心实现类（默认工厂列表）
 * @author wj
 * @create 2022-10-09 17:40
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }
}
