package com.imwj.springframework.core.io;

/**
 * 资源加载
 * @author wj
 * @create 2022-11-01 17:27
 */
public interface ResourceLoader {

    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取需要加载资源流
     * @param location
     * @return
     */
    Resource getResource(String location);

}
