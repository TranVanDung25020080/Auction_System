package com.auction.server.dp.factory.IFac.add;

import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AddElectronics implements AddItem {
    @Override
    public void addToItem(PreparedStatement pst, Item item) throws SQLException {
        Electronics el = (Electronics) item;
        pst.setString(5, "ELECTRONICS");
        pst.setInt(7,el.getWarranty());
        pst.setNull(8, Types.VARCHAR);
        pst.setNull(9, Types.VARCHAR);
    }
}
