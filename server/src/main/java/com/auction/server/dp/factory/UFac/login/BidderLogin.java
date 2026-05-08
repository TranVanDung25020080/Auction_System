package com.auction.server.dp.factory.UFac.login;

import com.auction.common.model.User.Bidder;
import com.auction.common.model.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidderLogin implements UserLogin {
    @Override
    public User logUser(ResultSet rs) throws SQLException {
        String ownerName = rs.getString("ownerName");
        String userName = rs.getString("username");
        String password = rs.getString("password");

        return new Bidder(ownerName, userName, password);
    }
}
