package com.imwj.springframework.beans.factory;

/**
 * FactoryBean 接口
 * @author wj
 * @create 2022-11-17 14:58
 */
public interface FactoryBean<T> {

    /**
     * 获取对象
     * @return
     * @throws Exception
     */
    T getObject() throws Exception;

    /**
     * 获取对象类型
     * @return
     */
    Class<?> getObjectType();

    /**
     * 判断是否是单例bean
     * @return
     */
    boolean isSingleton();

}
