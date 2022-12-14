package com.imwj.springframework.utils;

/**
 * 解析字符串接口
 * @author wj
 * @create 2022-12-12 16:34
 */
public interface StringValueResolver {


    /**
     * 解析字符串的值
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);

}
