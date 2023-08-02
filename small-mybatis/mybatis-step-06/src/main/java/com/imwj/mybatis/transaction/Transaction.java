package com.imwj.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wj
 * @create 2023-07-26 16:37
 * @description 事务性接口
 */
public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

}
