package com.auction.server.db;

import com.auction.server.util.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabaseConfig {
    private static Connection connection;
    private static final String URL = ConfigLoader.get("db.url");
    private static final String USER = ConfigLoader.get("db.user");
    private static final String PASSWORD = ConfigLoader.get("db.password");

    public static Connection getConnection() throws SQLException {
        connection= DriverManager.getConnection(URL,USER,PASSWORD);
        return connection;
    }

}
