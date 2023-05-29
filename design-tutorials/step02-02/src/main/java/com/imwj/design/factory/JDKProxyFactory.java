package com.imwj.design.factory;

import com.imwj.design.workshop.ICacheAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * jdk代理工厂
 * @author wj
 * @create 2023-05-29 11:44
 */
public class JDKProxyFactory {

    public static <T> T getProxy(Class<T> cacheClazz, Class<? extends ICacheAdapter> cacheAdapter) throws Exception {
        InvocationHandler handler = new JDKInvocationHandler(cacheAdapter.newInstance());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 动态代理方法：Proxy.newProxyInstance(类加载器，需要代理的接口，执行handler.invoke方法)
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{cacheClazz}, handler);
    }
}
