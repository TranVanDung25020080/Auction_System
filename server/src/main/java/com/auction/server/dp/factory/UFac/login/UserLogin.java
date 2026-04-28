package com.auction.server.dp.factory.UFac.login;

import com.auction.common.model.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserLogin {
    User logUser(ResultSet rs) throws SQLException;
}
