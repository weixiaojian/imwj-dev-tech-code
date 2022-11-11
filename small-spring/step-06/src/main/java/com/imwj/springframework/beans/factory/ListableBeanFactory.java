package com.imwj.springframework.beans.factory;

import com.imwj.springframework.beans.BeansException;

import java.util.Map;

/**
 * beanFactory列表
 * @author wj
 * @create 2022-11-01 17:23
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 按照类型返回 Bean 实例
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();

}