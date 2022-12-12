package com.imwj.springframework.beans.factory;

/**
 * 实现此接口，既能感知到所属的 ClassLoader
 * @author wj
 * @create 2022-11-16 15:44
 */
public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader);

}