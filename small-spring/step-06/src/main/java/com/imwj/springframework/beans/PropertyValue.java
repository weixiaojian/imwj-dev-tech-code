package com.imwj.springframework.beans;

/**
 * bean里面的属性
 * @author wj
 * @create 2022-10-25 11:29
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
