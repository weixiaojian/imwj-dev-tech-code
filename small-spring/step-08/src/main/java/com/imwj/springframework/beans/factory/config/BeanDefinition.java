package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.PropertyValues;

/**
 * 创建类所需的BeanDefinition
 * @author wj
 * @create 2022-10-25 11:28
 */
public class BeanDefinition {

    /**
     * 类的class属性
     */
    private Class beanClass;

    /**
     * 类所需填充的属性
     */
    private PropertyValues propertyValues;

    /**
     * xml中的init-method配置项
     */
    private String initMethodName;
    /**
     * xml中destroy-method配置项
     */
    private String destroyMethodName;


    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
