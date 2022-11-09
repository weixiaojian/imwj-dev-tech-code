package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.core.io.Resource;
import com.imwj.springframework.core.io.ResourceLoader;

/**
 * @author wj
 * @create 2022-11-01 17:22
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}
