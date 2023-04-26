package com.imwj.main;

import java.sql.*;

/**
 * @author wj
 * @create 2023-04-26 16:22
 */
public class Main {

    public static void main(String[] args) {
        try {
            // 初始化驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 数据库连接
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:mysql://127.0.0.1:3306/tale?characterEncoding=UTF-8",
                            "root", "123456");
            System.out.println("连接成功，获取连接对象： " + c);

            Statement s = c.createStatement();
            PreparedStatement ps = c.prepareStatement("select * from t_attach");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
