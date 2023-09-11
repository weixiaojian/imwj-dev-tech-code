package com.imwj.mybatis.transaction.jdbc;

import com.imwj.mybatis.session.TransactionIsolationLevel;
import com.imwj.mybatis.transaction.Transaction;
import com.imwj.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author wj
 * @create 2023-07-26 16:44
 * @description jdbc事务工厂实现类
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}
