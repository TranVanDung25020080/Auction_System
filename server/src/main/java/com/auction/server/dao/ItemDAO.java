package com.auction.server.dao;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import com.auction.common.model.User.Seller;
import com.auction.server.db.DatabaseConnection;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import com.auction.server.dp.factory.IFac.add.*;
import com.auction.server.dp.factory.IFac.get.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemDAO {
    private static final HashMap<String, GetItem> items = new HashMap<>();

    private static final HashMap<Class< ? extends Item>, AddItem> adds = new HashMap<>();

    static {
        items.put("ELECTRONICS", new GetElectronics());
        items.put("VEHICLE", new GetVehicle());
        items.put("ART", new GetArt());

        adds.put(Electronics.class, new AddElectronics());
        adds.put(Art.class, new AddArt());
        adds.put(Vehicle.class, new AddVehicle());
    }

    public boolean addItem(Item item) throws Exception {
        String query = "INSERT INTO item (name, description, initialPrice, item_type, seller_id, warranty, company, author, item_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*
        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(2, item.getName());
            pst.setString(3, item.getDescription());
            pst.setDouble(4, item.getInitialPrice());
            pst.setInt(6, item.getSeller().getId());
            pst.setString(10, ItemStatus.AVAILABLE.name());

            AddItem ai = adds.get(item.getClass());

            if (ai == null) {
                throw new IllegalArgumentException("He thong chua ho tro dang ki cho loai vat pham nay!!");
            }
            ai.addToItem(pst, item);


            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Loi SQL o ham addItem: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the them san pham moi", e);
        }
    }

    public Item getItemById(int itemId) throws DatabaseException {
        String query = "SELECT * FROM item WHERE id = ?";

/*        try (Connection conn = DatabaseConnection.getConnection();*/
          try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, itemId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String type = rs.getString("item_type");
                GetItem gi = items.get(type);
                if (gi == null) {
                    throw new IllegalArgumentException("Hien khong co loai vat pham nay");
                }
                return gi.getItem(rs);
            }
        } catch (SQLException e) {
            System.err.println("Loi SQL o ham getItemById: " + e.getMessage());
            throw new DatabaseException("Loi he thong: Khong the truy xuat thong tin san pham.", e);
        }
        return null;
    }

    public List<Item> getAllItems() throws DatabaseException {
        List<Item> itemList = new ArrayList<>();
        String query = "SELECT * FROM item";

/*        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("item_type");
                GetItem gi = items.get(type);
                if (gi == null) {
                    throw new IllegalArgumentException("Danh sach ton tai vat pham" + rs.getString("id") + "khong xac dinh loai");
                }
                itemList.add(gi.getItem(rs));
            }
            return itemList;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL ở hàm getAllItems: " + e.getMessage());
            throw new DatabaseException("Lỗi hệ thống: Không thể tải danh sách sản phẩm.", e);
        }
    }

    public List<Item> getItemBySellerId(int sellerId) throws DatabaseException {
        String query = "SELECT * FROM item WHERE seller_id = ?";
        List<Item> itemList = new ArrayList<>();

/*        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, sellerId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String type = rs.getString("item_type");
                GetItem gi = items.get(type);
                if (gi == null) {
                    throw new IllegalArgumentException("Hien khong co loai vat pham nay");
                }
                itemList.add(gi.getItem(rs));
            }
            return itemList;
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham getItemBySellerId: " + e.getMessage());
            throw new DatabaseException("Loi he thong: Khong the lay vat pham theo id nguoi ban.", e);
        }
    }

   /* //test
    static void main(String[] args) throws DatabaseException {
        Item item=new ItemDAO().getItemById(1);
        item.printInfo();
    }*/

}