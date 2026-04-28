package com.auction.server.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123Dung#";

    private static HikariDataSource ds;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(5);
            config.setConnectionTimeout(10000);

            ds = new HikariDataSource(config);
        }
        catch (Exception e) {
            System.out.println("Loi khoi tao connection pool");
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closeConnectionPool() {
        if (!ds.isClosed() || ds != null) {
            ds.close();
            System.out.println("Connection pool is closed.");
        }
    }
}
