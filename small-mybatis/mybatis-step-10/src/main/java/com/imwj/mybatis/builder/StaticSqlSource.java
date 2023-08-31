package com.imwj.mybatis.builder;

import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.ParameterMapping;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.session.Configuration;

import java.util.List;

/**
 * @author wj
 * @create 2023-08-18 17:14
 * @description  静态SQL源码
 */
public class StaticSqlSource implements SqlSource {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }

}