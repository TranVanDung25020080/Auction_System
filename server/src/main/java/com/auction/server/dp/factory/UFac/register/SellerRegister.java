package com.auction.server.dp.factory.UFac.register;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class SellerRegister implements UserRegister {
    @Override
    public void setParameter(PreparedStatement pst) throws SQLException {
        pst.setString(4, "SELLER");
        pst.setNull(5, Types.VARCHAR);
        pst.setDouble(6, 5.0);
        pst.setDouble(7, 0.0);
    }
}
