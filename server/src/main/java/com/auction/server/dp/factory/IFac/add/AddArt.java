package com.auction.server.dp.factory.IFac.add;

import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Item;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AddArt implements AddItem {
    @Override
    public void addToItem(PreparedStatement pst, Item item) throws SQLException {
        Art ar = (Art) item;
        pst.setString(4, "ART");
        pst.setNull(6, Types.INTEGER);
        pst.setNull(7, Types.VARCHAR);
        pst.setString(8, ar.getAuthor());
    }
}
