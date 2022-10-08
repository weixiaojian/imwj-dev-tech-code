package com.imwj.springframework;

/**
 * 定义 Bean 实例化信息
 * @author wj
 * @create 2022-10-08 17:36
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
