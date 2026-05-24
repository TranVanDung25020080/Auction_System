package com.auction.server.dp.factory.UFac.login;

import com.auction.common.model.User.Seller;
import com.auction.common.model.User.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerLogin implements UserLogin {
    @Override
    public User logUser(ResultSet rs) throws SQLException {
        return new Seller(
                rs.getInt("userId"),
                rs.getString("ownerName"),
                rs.getString("username"),
                rs.getString("password") != null ? rs.getString("password").trim() : "",
                rs.getDouble("rating"),
                rs.getDouble("balance")
        );
    }
}