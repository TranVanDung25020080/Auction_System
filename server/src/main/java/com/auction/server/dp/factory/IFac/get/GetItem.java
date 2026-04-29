package com.auction.server.dp.factory.IFac.get;

import com.auction.common.model.Item.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GetItem {
    Item getItem(ResultSet rs) throws SQLException;
}
