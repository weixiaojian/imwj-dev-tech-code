package com.imwj.springframework.factory;

import com.imwj.springframework.BeansException;

/**
 * Bean 对象的工厂
 * @author wj
 * @create 2022-10-08 17:37
 */
public interface BeanFactory {


    public Object getBean(String name) throws BeansException;

}
