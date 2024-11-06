package com.ityang.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/db01";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
//todo 后续需要使得密码加密
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            System.out.println("数据库连接失败: " + e.getMessage());
        }
        return connection;
    }
}
