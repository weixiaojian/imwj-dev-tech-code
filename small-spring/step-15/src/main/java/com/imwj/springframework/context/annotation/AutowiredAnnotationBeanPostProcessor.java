package com.imwj.springframework.context.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.PropertyValues;
import com.imwj.springframework.beans.factory.BeanFactory;
import com.imwj.springframework.beans.factory.BeanFactoryAware;
import com.imwj.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.imwj.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.imwj.springframework.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * 扫描Autowired等注解 并处理
 * @author wj
 * @create 2022-12-12 17:39
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 1.处理@Value
        Class<?> clazz = bean.getClass();
        // 如果是代理类 则通过getSuperclass获取到原始类
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        // 得到所有字段
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field: declaredFields) {
            // 获取属性上的@Value注解
            Value valueAnnotation = field.getAnnotation(Value.class);
            if(valueAnnotation != null){
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 2.处理@Autowired
        for (Field field: declaredFields) {
            // 获取属性上的@Autowired注解
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if(autowiredAnnotation != null){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Object dependentBean = null;
                // 获取属性上的@Qualifier注解
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                if(qualifierAnnotation != null){
                    // 根据名称和类型获取
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                }else{
                    // 根据类型获取
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }


}
