package com.auction.server.dp.factory.IFac.get;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.User.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetElectronics implements GetItem {
    @Override
    public Item getItem(ResultSet rs) throws SQLException {
        Seller seller_id = new Seller(
                rs.getString("seller_id"),
                null,null,null,0.0,0.0);
        return new Electronics(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("initialPrice"),
                seller_id,
                ItemStatus.valueOf(rs.getString("item_status")),
                rs.getInt("warranty"));
    }
}
