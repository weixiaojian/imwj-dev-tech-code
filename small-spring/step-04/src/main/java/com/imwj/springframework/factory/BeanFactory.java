package com.imwj.springframework.factory;

import com.imwj.springframework.BeansException;

/**
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

}
