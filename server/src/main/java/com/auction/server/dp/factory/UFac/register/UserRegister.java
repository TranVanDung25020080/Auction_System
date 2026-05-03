package com.auction.server.dp.factory.UFac.register;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserRegister {
    void setParameter(PreparedStatement pst) throws SQLException;
}
