package com.imwj.mybatis.session.defaults;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.executor.Executor;
import com.imwj.mybatis.mapping.Environment;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.SqlSession;
import com.imwj.mybatis.session.SqlSessionFactory;
import com.imwj.mybatis.session.TransactionIsolationLevel;
import com.imwj.mybatis.transaction.Transaction;
import com.imwj.mybatis.transaction.TransactionFactory;

import java.sql.SQLException;

/**
 * @author wj
 * @create 2023-07-20 10:28
 * @description 默认的 DefaultSqlSessionFactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction  tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            // 创建执行器
            Executor executor = configuration.newExecutor(tx);
            // 创建DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }
}

