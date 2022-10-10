package com.imwj.springframework.factory.support;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.config.BeanDefinition;

/**
 * 抽象自动装配bean工程（创建bean）
 * @author wj
 * @create 2022-10-09 17:38
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        }catch (Exception e){
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 创建成功后将bean放入到单例池中
        addSingleton(beanName, bean);
        return bean;
    }


}
