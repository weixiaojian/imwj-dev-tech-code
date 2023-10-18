package com.imwj.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @author wj
 * @create 2023-08-14 18:03
 * @description
 */
public class SetFieldInvoker implements Invoker{

    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        field.set(target, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
