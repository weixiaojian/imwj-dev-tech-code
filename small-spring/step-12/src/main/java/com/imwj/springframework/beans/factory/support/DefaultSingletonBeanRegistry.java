package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.DisposableBean;
import com.imwj.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认单例bean注册器
 * @author wj
 * @create 2022-10-11 16:39
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    /**
     * 单例bean缓存对象（key:beanName  value:Bean）
     */
    Map<String, Object> singletonObjects = new HashMap<>();
    /**
     * 销毁Bean缓存对象（key:beanName  value:Bean）
     */
    Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 添加单例bean
     * @param beanName
     * @param singletonBean
     */
    protected void addSingleton(String beanName, Object singletonBean){
        singletonObjects.put(beanName, singletonBean);
    }

    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 获取单例bean对象
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 注册bean销毁方法
     * @param beanName
     * @param bean
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    /**
     * 销毁单例bean的时候执行destroy方法
     */
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

}
