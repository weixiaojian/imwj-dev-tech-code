package com.imwj.mybatis.mapping;

/**
 * @author wj
 * @create 2023-08-18 16:05
 * @description SqlSource
 */
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);

}
