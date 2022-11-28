package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.config.BeanPostProcessor;
import com.imwj.springframework.context.ApplicationContext;
import com.imwj.springframework.context.ApplicationContextAware;

/**
 * 应用Aware处理器（包装处理器）
 * @author wj
 * @create 2022-11-16 15:45
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware)bean).setApplicationContextAware(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
