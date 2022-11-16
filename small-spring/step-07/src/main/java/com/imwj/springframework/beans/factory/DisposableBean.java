package com.imwj.springframework.beans.factory;

/**
 * 销毁Bean接口
 * @author wj
 * @create 2022-11-15 17:14
 */
public interface DisposableBean {

    /**
     * Bean销毁后调用
     * @throws Exception
     */
    void destroy() throws Exception;

}
