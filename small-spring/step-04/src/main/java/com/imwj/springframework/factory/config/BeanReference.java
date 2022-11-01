package com.imwj.springframework.factory.config;

/**
 * @author wj
 * @create 2022-10-25 11:28
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
