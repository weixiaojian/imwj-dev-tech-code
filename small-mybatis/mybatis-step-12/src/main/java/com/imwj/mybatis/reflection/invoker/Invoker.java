package com.imwj.mybatis.reflection.invoker;

/**
 * @author wj
 * @create 2023-08-14 17:55
 * @description 调用者
 */
public interface Invoker {

    Object invoke(Object target, Object[] args) throws Exception;

    Class<?> getType();

}
