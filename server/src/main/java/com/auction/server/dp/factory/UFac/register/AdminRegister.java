package com.auction.server.dp.factory.UFac.register;

import com.auction.common.model.User.Admin;
import com.auction.common.model.User.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AdminRegister implements UserRegister {
    @Override
    public void setParameter(PreparedStatement pst, User user) throws SQLException {
        Admin ad = (Admin) user;
        pst.setString(5, "ADMIN");
        pst.setString(6, ad.getRoleLevel());
        pst.setNull(7, Types.VARCHAR);
        pst.setNull(8, Types.DOUBLE);
        pst.setNull(9, Types.DECIMAL);
    }
}
