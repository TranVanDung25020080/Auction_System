package com.auction.server.db;

import com.auction.server.util.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabaseConfig {
    private static Connection connection;
    private static final String URL= ConfigLoader.get("db.url");
    private static final String USER= ConfigLoader.get("db.user");
    private static final String PASSWORD= ConfigLoader.get("db.password");

//        private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
//        private static final String USER = "root";
//        private static final String PASSWORD = "123Dung#";

//    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

//    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "haidang20022007@";

    public static Connection getConnection() throws SQLException {
        connection= DriverManager.getConnection(URL,USER,PASSWORD);
        return connection;
    }

}
