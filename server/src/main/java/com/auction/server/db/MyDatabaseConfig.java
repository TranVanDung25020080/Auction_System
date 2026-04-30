package com.auction.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabaseConfig {
    private static Connection connection;
    private static final String URL="jdbc:mysql://localhost:3306/auctions";
    private static final String user="root";
    private static final String password="";

    public static Connection getConnection() throws SQLException {
        connection= DriverManager.getConnection(URL,user,password);
        return connection;
    }

}
