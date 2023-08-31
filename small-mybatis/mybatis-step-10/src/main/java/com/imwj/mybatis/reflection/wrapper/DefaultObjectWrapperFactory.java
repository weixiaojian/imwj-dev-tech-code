package com.imwj.mybatis.reflection.wrapper;

import com.imwj.mybatis.reflection.MetaObject;

/**
 * @author wj
 * @create 2023-08-17 17:32
 * @description  默认对象包装工厂
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory{

    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new RuntimeException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }
}
