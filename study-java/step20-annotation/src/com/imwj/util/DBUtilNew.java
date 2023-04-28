package com.imwj.util;

import com.imwj.annotation.JDBCConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wj
 * @create 2023-04-27 14:41
 */
@JDBCConfig(ip = "127.0.0.1", database = "tale", encoding = "UTF-8", loginName = "root", password = "123456")
public class DBUtilNew {

    // 加载驱动
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        // 得到注解
        JDBCConfig jdbcConfig = DBUtilNew.class.getAnnotation(JDBCConfig.class);
        // 获取注解中的数据 并创建连接
        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", jdbcConfig.ip(), jdbcConfig.port(),
                jdbcConfig.database(), jdbcConfig.encoding());
        return DriverManager.getConnection(url, jdbcConfig.loginName(), jdbcConfig.password());
    }

    public static void main(String[] args) throws Exception {
        Connection c = getConnection();
        System.out.println(c);
    }
}
