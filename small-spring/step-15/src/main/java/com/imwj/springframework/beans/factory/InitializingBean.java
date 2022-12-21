package com.imwj.springframework.beans.factory;

import com.imwj.springframework.beans.BeansException;

/**
 * 初始化bean接口
 * @author wj
 * @create 2022-11-15 17:12
 */
public interface InitializingBean {

    /**
     * Bean处理了属性填充后调用
     * @throws BeansException
     */
    void afterPropertiesSet() throws Exception;

}
