package com.imwj.mybatis.executor.statement;

import com.imwj.mybatis.executor.Executor;
import com.imwj.mybatis.executor.parameter.ParameterHandler;
import com.imwj.mybatis.executor.resultset.ResultSetHandler;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.ResultHandler;
import com.imwj.mybatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wj
 * @create 2023-08-08 11:42
 * @description
 */
public abstract class BaseStatementHandler implements StatementHandler{

    protected final Configuration configuration;
    protected final Executor executor;
    protected final MappedStatement mappedStatement;
    protected final Object parameterObject;
    protected final ResultSetHandler resultSetHandler;
    protected final ParameterHandler parameterHandler;

    protected final RowBounds rowBounds;
    protected BoundSql boundSql;


    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.boundSql = boundSql;

        this.parameterObject = parameterObject;
        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, resultHandler, boundSql);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            // 实例化Statement
            statement = instantiateStatement(connection);
            // 参数设置，可以被抽取，提供配置
            statement.setQueryTimeout(350);
            statement.setFetchSize(10000);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;

}
