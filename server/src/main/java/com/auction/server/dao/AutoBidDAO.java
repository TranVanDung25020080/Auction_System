package com.auction.server.dao;

import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AutoBidDAO {
    //insert
    public void insertAutoBid(int bidderId, int auctionId, double maxBid, double increment) throws DatabaseException {
        String query = "INSERT INTO auto_bid (bidderId, auctionId, maxBid, increment, autoBidTime)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, bidderId);
            pst.setInt(2, auctionId);
            pst.setDouble(3, maxBid);
            pst.setDouble(4, increment);
            pst.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            pst.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Loi SQL o ham insertAutoBid: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the them autobid", e);
        }
    }
    //update
    public void updateAutoBid(int bidderId, double maxBid, double increment) throws DatabaseException {
        String query = "UPDATE auto_bid SET maxBid = ?, increment = ? WHERE bidderId = ?";

        try (Connection conn = MyDatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setDouble(1, maxBid);
                pst.setDouble(2, increment);
                pst.setInt(3, bidderId);

                pst.executeUpdate();
                conn.commit();
                System.out.println("Cap nhat thong tin auto bid thanh cong");
            }
            catch (SQLException e) {
                conn.rollback();
                throw new DatabaseException("Loi he thong: khong the cap nhat autobid, tu dong rollback", e);
            }
        }
        catch (SQLException e) {
            System.out.println("Loi SQL o ham updateAutoBid: " + e.getMessage());
        }
    }
}
