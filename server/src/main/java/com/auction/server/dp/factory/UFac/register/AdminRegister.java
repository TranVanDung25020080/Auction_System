package com.auction.server.dp.factory.UFac.register;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AdminRegister implements UserRegister {
    @Override
    public void setParameter(PreparedStatement pst) throws SQLException {
        pst.setString(4, "ADMIN");
        pst.setString(5, "AUCTIONEER");
        pst.setNull(6, Types.DOUBLE);
        pst.setNull(7, Types.DECIMAL);
    }
}
