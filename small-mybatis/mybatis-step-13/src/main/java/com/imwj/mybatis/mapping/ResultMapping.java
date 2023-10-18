package com.imwj.mybatis.mapping;

import cn.hutool.db.meta.JdbcType;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.type.TypeHandler;

/**
 * @author wj
 * @create 2023-08-31 11:24
 * @description 结果映射
 */
public class ResultMapping {

    private Configuration configuration;
    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;
    private TypeHandler<?> typeHandler;


    ResultMapping() {
    }

    public static class Builder {
        private ResultMapping resultMapping = new ResultMapping();
    }

}
