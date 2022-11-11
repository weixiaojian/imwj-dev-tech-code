package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.BeansException;

/**
 * Bean后置处理器
 * @author wj
 * @create 2022-11-04 15:37
 */
public interface BeanPostProcessor {


    /**
     * 在bean初始化前执行该方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName)throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;


}
