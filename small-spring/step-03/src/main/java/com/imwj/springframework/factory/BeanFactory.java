package com.imwj.springframework.factory;

import com.imwj.springframework.BeansException;

/**
 * @author wj
 * @create 2022-10-11 16:34
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
