package com.auction.server.dp.factory.UFac.login;

import com.auction.common.model.User.Bidder;
import com.auction.common.model.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidderLogin implements UserLogin {
    @Override
    public User logUser(ResultSet rs) throws SQLException {
        return new Bidder(
                rs.getInt("userId"),
                rs.getString("ownerName"),
                rs.getString("userName"),
                rs.getDouble("balance"));
    }
}
