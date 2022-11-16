package com.imwj.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * bean里面的属性集合
 * @author wj
 * @create 2022-10-25 11:29
 */
public class PropertyValues {

    /**
     * 存储bean里面属性值的集合
     */
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

}
