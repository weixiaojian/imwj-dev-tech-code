package com.imwj.springframework.core.io;

/**
 * @author wj
 * @create 2022-11-01 17:27
 */
public interface ResourceLoader {

    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);

}
