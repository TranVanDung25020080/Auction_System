package com.auction.server.dao;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.db.DatabaseConnection;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;

import java.sql.*;

public class AuctionDAO {

    public boolean createAuction(Auction auction) throws DatabaseException {
        String query = "INSERT INTO auction (auctionId, itemId, currentHighestPrice, winningBidderId, startTime, endTime, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        /*try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, auction.getAuctionId());
            pst.setInt(2, auction.getItemId());
            pst.setDouble(3, auction.getCurrentHighestPrice());
            pst.setInt(4, auction.getWinningBidderId());
            pst.setTimestamp(5, Timestamp.valueOf(auction.getStartTime()));
            pst.setTimestamp(6, Timestamp.valueOf(auction.getEndTime()));
            pst.setString(7, auction.getStatus().toString());

            int change = pst.executeUpdate();
            return (change > 0);

        }
        catch (SQLException e) {
            System.out.println("Loi SQL o ham updateCurrentPrice: " + e.getMessage());
            throw new DatabaseException("Loi he thong: Khong the tao cuoc dau gia", e);
        }
    }

    public void updateCurrentPrice(int auctionId, double newPrice, int bidderId) throws DatabaseException {
        String query = "UPDATE auction SET currentHighestPrice = ? , winningBidderId = ? WHERE auctionId = ?";

        /*try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setDouble(1,newPrice);
            pst.setInt(2, bidderId);
            pst.setInt(3, auctionId);

        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham updateCurrentPrice: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the cap nhat gia vat pham",e);
        }
    }

    public Auction getAuctionInfoById(int auctionId) throws DatabaseException {
        String query = "SELECT * FROM auction WHERE auctionId = ?";

/*        try (Connection conn = DatabaseConnection.getConnection();*/
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, auctionId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Auction(rs.getInt("auctionId"),
                            rs.getInt("itemId"),
                            rs.getDouble("currentHighestPrice"),
                            rs.getInt("winningBidderId"),
                            rs.getTimestamp("startTime").toLocalDateTime(),
                            rs.getTimestamp("endTime").toLocalDateTime(),
                            AuctionStatus.valueOf(rs.getString("status")));
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham getAuctionInfoById: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the xem thong tin cuoc dau gia",e);
        }
        return null;
    }

}
