package com.auction.server.dp.factory.IFac.get;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import com.auction.common.model.User.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVehicle implements GetItem {
    @Override
    public Item getItem(ResultSet rs) throws SQLException {
        Seller seller_id = new Seller(
                rs.getInt("seller_id"),
                null,null,0.0,0.0);
        return new Vehicle(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("initialPrice"),
                seller_id,
                ItemStatus.valueOf(rs.getString("item_status")),
                rs.getString("company"));
    }
}
