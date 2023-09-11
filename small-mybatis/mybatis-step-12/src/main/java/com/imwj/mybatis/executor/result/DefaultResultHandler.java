package com.imwj.mybatis.executor.result;

import com.imwj.mybatis.reflection.factory.ObjectFactory;
import com.imwj.mybatis.session.ResultContext;
import com.imwj.mybatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2023-08-31 17:33
 * @description 默认结果处理器
 */
public class DefaultResultHandler implements ResultHandler {

    private final List<Object> list;

    public DefaultResultHandler() {
        this.list = new ArrayList<>();
    }

    /**
     * 通过 ObjectFactory 反射工具类，产生特定的 List
     */
    @SuppressWarnings("unchecked")
    public DefaultResultHandler(ObjectFactory objectFactory) {
        this.list = objectFactory.create(List.class);
    }

    @Override
    public void handleResult(ResultContext context) {
        list.add(context.getResultObject());
    }

    public List<Object> getResultList() {
        return list;
    }

}
