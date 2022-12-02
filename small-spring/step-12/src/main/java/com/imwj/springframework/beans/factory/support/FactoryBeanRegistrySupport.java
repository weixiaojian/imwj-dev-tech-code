package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FactoryBean注册服务
 * @author wj
 * @create 2022-11-17 15:00
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    /**
     * FactoryBean创建的单例对象的缓存（key:factoryBeanName  value:factoryBeanObject）
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 根据beanName获取到缓存的Object
     * @param beanName
     * @return
     */
    protected Object getCachedObjectForFactoryBean(String beanName){
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 根据beanName和factory获取到缓存的Object（如果获取到就放到缓存中方便下次获取）
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName){
        if(factory.isSingleton()){
            Object object = this.factoryBeanObjectCache.get(beanName);
            if(object == null){
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName,  (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        }else{
            // 不是单例的直接返回 无需缓存
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 根据factory获取到缓存的Object
     * @param factory
     * @param beanName
     * @return
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }


}
