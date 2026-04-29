package com.auction.server.dp.factory.UFac.register;

import com.auction.common.model.User.Bidder;
import com.auction.common.model.User.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BidderRegister implements UserRegister {
    @Override
    public void setParameter(PreparedStatement pst, User user) throws SQLException {
        Bidder bd = (Bidder) user;
        pst.setString(5, "BIDDER");
        pst.setNull(6, Types.VARCHAR);
        pst.setNull(7, Types.VARCHAR);
        pst.setNull(8, Types.DOUBLE);
        pst.setDouble(9,bd.getBalance());
    }
}
