package com.imwj.springframework.beans.factory;

import com.imwj.springframework.beans.BeansException;

/**
 * BeanFactory
 * @author wj
 * @create 2022-10-25 11:27
 */
public interface BeanFactory {

    /**
     * 根据beanName获取到bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 有参-根据beanName获取到bean
     * @param beanName
     * @param args
     * @return
     */
    Object getBean(String beanName, Object... args) throws BeansException;

    /**
     * 根据beanName和beanType获取
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 根据beanType获取
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;
}
