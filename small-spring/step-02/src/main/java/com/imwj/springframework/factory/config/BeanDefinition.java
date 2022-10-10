package com.imwj.springframework.factory.config;

/**
 * 定义 Bean 实例化信息
 * @author wj
 * @create 2022-10-08 17:36
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
