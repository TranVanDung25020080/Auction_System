package com.auction.server.dp.factory.UFac.login;

import com.auction.common.model.User.Admin;
import com.auction.common.model.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLogin implements UserLogin {
    @Override
    public User logUser(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("userId"),
                rs.getString("ownerName"),
                rs.getString("userName"),
                rs.getDouble("balance"));
    }
}
