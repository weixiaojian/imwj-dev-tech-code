package com.imwj.mybatis.session;

/**
 * @author wj
 * @create 2023-08-31 17:57
 * @description 结果上下文
 */
public interface ResultContext {

    /**
     * 获取结果
     */
    Object getResultObject();

    /**
     * 获取记录数
     */
    int getResultCount();
}
