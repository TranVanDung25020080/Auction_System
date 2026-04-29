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
        pst.setString(5, "VEHICLE");
        pst.setNull(7, Types.INTEGER);
        pst.setString(8, vh.getCompany());
        pst.setNull(9, Types.VARCHAR);
    }
}
