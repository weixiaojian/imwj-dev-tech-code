package com.imwj.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @author wj
 * @create 2023-08-14 18:01
 * @description
 */
public class GetFieldInvoker implements Invoker{

    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return field.get(target);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
