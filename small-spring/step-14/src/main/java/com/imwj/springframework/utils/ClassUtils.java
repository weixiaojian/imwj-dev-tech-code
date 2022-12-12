package com.imwj.springframework.utils;

import com.imwj.springframework.context.ApplicationListener;

/**
 * 类工具
 * @author wj
 * @create 2022-11-01 17:26
 */
public class ClassUtils {

    /**
     * 获取默认的类加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    /**
     * 判断是否是cglib实例化
     * @param listenerClass
     * @return
     */
    public static boolean isCglibProxyClass(Class<? extends ApplicationListener> listenerClass) {
        return true;
    }
}
