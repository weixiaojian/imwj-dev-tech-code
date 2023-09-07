package com.imwj.mybatis.scripting.defaults;

import com.imwj.mybatis.builder.SqlSourceBuilder;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.scripting.xmltags.DynamicContext;
import com.imwj.mybatis.scripting.xmltags.SqlNode;
import com.imwj.mybatis.session.Configuration;

import java.util.HashMap;

/**
 * @author wj
 * @create 2023-08-18 17:01
 * @description 原始SQL源码，比 DynamicSqlSource 动态SQL处理快
 */
public class RawSqlSource implements SqlSource {

    private final SqlSource sqlSource;

    public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }

    public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }

    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration, null);
        rootSqlNode.apply(context);
        return context.getSql();
    }

}
