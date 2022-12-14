package com.imwj.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.imwj.springframework.context.stereotype.Component;
import com.sun.org.apache.regexp.internal.RE;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 类具体扫描包处理的类
 * @author wj
 * @create 2022-12-09 17:32
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    /**
     * beanDefinition存储类
     */
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 指定路径扫描操作
     * @param basePackages
     */
    public void doScan(String... basePackages){
        for(String basePackage : basePackages){
            Set<BeanDefinition> candidates = findCandidateComonents(basePackage);
            for(BeanDefinition beanDefinition : candidates){
                // 解析bean的作用域
                String beanScope = resolveBeanScope(beanDefinition);
                if(StrUtil.isNotBlank(beanScope)){
                    beanDefinition.setScope(beanScope);
                }
                // 注册beanDefinition
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
            // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
            // 由于AutowiredAnnotationBeanPostProcessor并没有标注@Component,所以是无法在类扫描时注入到beanFactory中的,此处需要我们手动进行注册
            registry.registerBeanDefinition("autowiredAnnotationBeanPostProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
        }
    }


    /**
     * 解析bean的作用域
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if(scope != null){
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取beanName操作
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        // 如果没有指定beanName则默认使用bean简写
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
