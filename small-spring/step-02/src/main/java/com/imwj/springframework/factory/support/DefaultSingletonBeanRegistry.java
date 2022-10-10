package com.imwj.springframework.factory.support;

import com.imwj.springframework.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认单例bean注册
 * @author wj
 * @create 2022-10-09 17:41
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String,Object> singletonObjects = new HashMap<>();


    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 添加单例bean
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
