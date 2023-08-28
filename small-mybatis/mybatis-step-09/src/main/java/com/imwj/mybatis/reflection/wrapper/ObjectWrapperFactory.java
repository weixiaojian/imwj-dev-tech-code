package com.imwj.mybatis.reflection.wrapper;

import com.imwj.mybatis.reflection.MetaObject;

/**
 * @author wj
 * @create 2023-08-17 17:24
 * @description 对象包装工厂
 */
public interface ObjectWrapperFactory {

    /**
     * 判断有没有包装器
     */
    boolean hasWrapperFor(Object object);

    /**
     * 得到包装器
     */
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
