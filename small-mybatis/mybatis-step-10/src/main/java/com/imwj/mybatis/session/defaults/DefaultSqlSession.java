package com.imwj.mybatis.session.defaults;

import cn.hutool.core.thread.SemaphoreRunnable;
import com.imwj.mybatis.executor.Executor;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.Environment;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.RowBounds;
import com.imwj.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2023-07-18 17:45
 * @description 默认的 DefaultSqlSession
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) throws SQLException, ClassNotFoundException {
        MappedStatement ms  = configuration.getMappedStatement(statement);
        List<T> list  = executor.query(ms, parameter, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getSqlSource().getBoundSql(parameter));
        return list.get(0);
    }

    /**
     * 获取代理类
     * @param type Mapper interface class
     * @return
     * @param <T>
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
