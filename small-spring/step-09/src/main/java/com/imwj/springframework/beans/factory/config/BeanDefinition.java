package com.imwj.springframework.beans.factory.config;

import com.imwj.springframework.beans.PropertyValues;

/**
 * 创建类所需的BeanDefinition
 * @author wj
 * @create 2022-10-25 11:28
 */
public class BeanDefinition {

    /**
     * bean的类型（单例和原型）
     */
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

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

    /**
     * 默认单例bean
     */
    private String scope = SCOPE_SINGLETON;
    private boolean singleton = true;
    private boolean prototype = false;


    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    /**
     * 在xml注册Bean定义时，通过scope字段来判断是单例还是原型
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
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

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public void setPrototype(boolean prototype) {
        this.prototype = prototype;
    }
}
