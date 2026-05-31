package com.auction.server.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabaseConfig {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123Dung#";

    public static Connection getConnection() throws SQLException {
        connection= DriverManager.getConnection(URL,USER,PASSWORD);
        return connection;
    }
}
