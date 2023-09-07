package com.imwj.mybatis.session;

/**
 * @author wj
 * @create 2023-08-07 17:46
 * @description 结果处理器
 */
public interface ResultHandler {

    /**
     * 处理结果
     */
    void handleResult(ResultContext context);
}
