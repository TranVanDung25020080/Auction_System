package com.auction.server.dp.factory.UFac.register;

import com.auction.common.model.User.Seller;
import com.auction.common.model.User.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class SellerRegister implements UserRegister {
    @Override
    public void setParameter(PreparedStatement pst, User user) throws SQLException {
        Seller sl = (Seller) user;
        pst.setString(5, "SELLER");
        pst.setNull(6, Types.VARCHAR);
        pst.setString(7,sl.getShopName());
        pst.setDouble(8, sl.getRating());
        pst.setDouble(9, sl.getBalance());
    }
}
