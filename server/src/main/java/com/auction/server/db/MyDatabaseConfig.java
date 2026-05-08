package com.auction.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabaseConfig {
    private static Connection connection;
//    private static final String URL="jdbc:mysql://localhost:3306/auctions";
//    private static final String user="root";
//    private static final String password="";

//    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "123Dung#";

//    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

    private static final String URL = "jdbc:mysql://localhost:3306/auction_db";
    private static final String USER = "root";
    private static final String PASSWORD = "haidang20022007@";

    public static Connection getConnection() throws SQLException {
        connection= DriverManager.getConnection(URL,USER,PASSWORD);
        return connection;
    }

}
