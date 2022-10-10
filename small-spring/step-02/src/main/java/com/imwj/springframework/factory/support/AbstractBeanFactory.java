package com.imwj.springframework.factory.support;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.BeanFactory;
import com.imwj.springframework.factory.config.BeanDefinition;

/**
 * 抽象工厂类（通过BeanDefinition获取bean）
 * @author wj
 * @create 2022-10-09 17:39
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        // 如果获取不到单例bena的话 则通过BeanDefinition来创建bean
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 通过BeanDefinition来创建bean
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;


}
