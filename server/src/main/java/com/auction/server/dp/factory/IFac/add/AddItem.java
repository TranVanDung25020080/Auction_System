package com.auction.server.dp.factory.IFac.add;

import com.auction.common.model.Item.Item;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface AddItem {
    void addToItem(PreparedStatement pst, Item item) throws SQLException;
}
