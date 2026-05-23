package com.auction.server.dp.factory.IFac.add;

import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AddVehicle implements AddItem {
    @Override
    public void addToItem(PreparedStatement pst, Item item) throws SQLException {
        Vehicle vh = (Vehicle) item;
        pst.setString(4, "VEHICLE");
        pst.setNull(6, Types.INTEGER);
        pst.setString(7, vh.getCompany());
        pst.setNull(8, Types.VARCHAR);
    }
}
