package com.imwj.springframework.factory.support;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * cglib实例化
 * @author wj
 * @create 2022-10-11 16:53
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        // 无参构造
        if(null == ctor) return enhancer.create();
        // 有参构造
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
