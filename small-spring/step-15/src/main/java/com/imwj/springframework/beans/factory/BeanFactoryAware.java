package com.imwj.springframework.beans.factory;

/**
 * 实现此接口，既能感知到所属的 beanFactory
 * @author wj
 * @create 2022-11-16 15:40
 */
public interface BeanFactoryAware extends Aware{

    /**
     * 设置beanFactory
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory);

}
