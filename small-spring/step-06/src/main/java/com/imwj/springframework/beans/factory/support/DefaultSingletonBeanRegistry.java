package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认单例bean注册器
 * @author wj
 * @create 2022-10-11 16:39
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    Map<String, Object> singletonObjects = new HashMap<>();

    protected void addSingleton(String beanName, Object singletonBean){
        singletonObjects.put(beanName, singletonBean);
    }


    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }
}
