package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.BeansException;

/**
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
     * 在bean初始化后执行该方法
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization()throws BeansException;

}
