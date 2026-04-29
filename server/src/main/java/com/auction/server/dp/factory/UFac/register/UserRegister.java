package com.auction.server.dp.factory.UFac.register;

import com.auction.common.model.User.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserRegister {
    void setParameter(PreparedStatement pst, User user) throws SQLException;
}
