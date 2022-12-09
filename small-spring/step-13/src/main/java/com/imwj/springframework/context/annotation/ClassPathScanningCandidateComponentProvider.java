package com.imwj.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.context.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 类路径扫描所有的bean处理程序
 * @author wj
 * @create 2022-12-09 17:23
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 得到指定类路径下所有bean的beanDefinition
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComonents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 扫描指定包路径下所有包含指定注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        // 封装beanDefinition返回
        for(Class<?> clazz : classes){
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}
