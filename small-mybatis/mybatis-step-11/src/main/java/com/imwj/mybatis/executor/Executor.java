package com.imwj.mybatis.executor;

import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.session.ResultHandler;
import com.imwj.mybatis.session.RowBounds;
import com.imwj.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author wj
 * @create 2023-08-07 17:43
 * @description 执行器接口
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    /**
     * update方法包含了：insert、update、delete方法
     */
    int update(MappedStatement ms, Object parameter) throws SQLException;

    /**
     * 查询方法
     */
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);

}
