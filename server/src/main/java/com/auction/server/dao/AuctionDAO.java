package com.auction.server.dao;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.db.DatabaseConnection;
import com.auction.server.exception.DatabaseException;

import java.sql.*;

public class AuctionDAO {

    boolean createAuction(Auction auction) throws DatabaseException {
        String query = "INSERT INTO auction (auctionId, itemId, currentHighestPrice, winningBidderId, startTime, endTime, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, auction.getAuctionId());
            pst.setString(2, auction.getItemId());
            pst.setDouble(3, auction.getCurrentHighestPrice());
            pst.setString(4, auction.getWinningBidderId());
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

    public boolean updateCurrentPrice(String itemId, double newPrice, String bidderId) throws DatabaseException {
        String query = "UPDATE auction SET currentHighestPrice = ? , winningBidderId = ? WHERE itemId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setDouble(1,newPrice);
            pst.setString(2, bidderId);
            pst.setString(3, itemId);

            int change = pst.executeUpdate();
            return change > 0;

        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham updateCurrentPrice: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the cap nhat gia vat pham",e);
        }
    }

    public Auction getAuctionInfoById(String auctionId) throws DatabaseException {
        String query = "SELECT * FROM auction WHERE auctionId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, auctionId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Auction(rs.getString("auctionId"),
                            rs.getString("itemId"),
                            rs.getDouble("currentHighestPrice"),
                            rs.getString("winningBidderId"),
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
