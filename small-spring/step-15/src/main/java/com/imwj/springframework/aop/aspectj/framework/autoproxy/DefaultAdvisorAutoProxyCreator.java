package com.imwj.springframework.aop.aspectj.framework.autoproxy;

import com.imwj.springframework.aop.*;
import com.imwj.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.imwj.springframework.aop.aspectj.framework.ProxyFactory;
import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.PropertyValues;
import com.imwj.springframework.beans.factory.BeanFactory;
import com.imwj.springframework.beans.factory.BeanFactoryAware;
import com.imwj.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.imwj.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 将aop代理融入bean的生命周期中（aop核心）
 * @author wj
 * @create 2022-12-07 15:42
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(isInfrastructureClass(beanClass)) {
            return null;
        }
        // 得到系统中所有的AspectJExpressionPointcutAdvisor类
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for(AspectJExpressionPointcutAdvisor advisor : advisors){
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 如果切入点和当前类不匹配则跳过
            if(!classFilter.matches(beanClass)){
                continue;
            }
            // 填充代理、拦截、匹配的各项属性包装类
            AdvisedSupport advisedSupport = new AdvisedSupport();
            // 目标对象实例化（通过beanClass反射生成）
            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher((MethodMatcher) advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);
            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return null;
    }

    /**
     * 判断目标类是否是Advice、Pointcut、Advisor的子类
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return null;
    }
}
