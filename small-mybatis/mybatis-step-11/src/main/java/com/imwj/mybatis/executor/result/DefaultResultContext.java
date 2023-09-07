package com.imwj.mybatis.executor.result;

import com.imwj.mybatis.session.ResultContext;

/**
 * @author wj
 * @create 2023-08-31 17:58
 * @description 默认结果上下文
 */
public class DefaultResultContext implements ResultContext {

    private Object resultObject;
    private int resultCount;

    public DefaultResultContext() {
        this.resultObject = null;
        this.resultCount = 0;
    }

    @Override
    public Object getResultObject() {
        return resultObject;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    public void nextResultObject(Object resultObject) {
        resultCount++;
        this.resultObject = resultObject;
    }

}
