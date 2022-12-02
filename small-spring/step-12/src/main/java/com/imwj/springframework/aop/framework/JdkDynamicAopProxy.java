package com.imwj.springframework.aop.framework;

import com.imwj.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理实现AOP
 * 必须实现同一个接口，没有接口不能实现jdk的动态代理
 * @author wj
 * @create 2022-12-01 17:04
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;


    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果能匹配到该类和该方法则通过拦截器调用目标方法
        if(advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())){
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        // 没有匹配到的话 则直接通过反射调用目标方法
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
