package com.auction.server.dp.factory.UFac.register;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BidderRegister implements UserRegister {
    @Override
    public void setParameter(PreparedStatement pst) throws SQLException {
        pst.setString(4, "BIDDER");
        pst.setNull(5, Types.VARCHAR);
        pst.setNull(6, Types.DOUBLE);
        pst.setDouble(7, 0.0);
    }
}
