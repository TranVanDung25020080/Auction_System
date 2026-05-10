package com.auction.server.dao;

import com.auction.common.model.Auction.BidTransaction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {

    UserDAO userDAO = new UserDAO();
    AuctionDAO auctionDAO = new AuctionDAO();

    public void placeBid(int auctionId, int bidderId, double bidAmount) throws DatabaseException {
        String insertBidQuery = "INSERT INTO bids (auction_id, bidder_id, bid_amount, bid_time) VALUES (?, ?, ?, NOW())";

        double bidderBalance = userDAO.showBalance(bidderId);

        try (Connection conn = MyDatabaseConfig.getConnection();
            PreparedStatement pst = conn.prepareStatement(insertBidQuery)) {
            if (bidderBalance >= bidAmount) {
                pst.setInt(1, auctionId);
                pst.setInt(2, bidderId);
                pst.setDouble(3, bidAmount);

                int result = pst.executeUpdate();
                if (result > 0) {
                    auctionDAO.updateCurrentPrice(auctionId, bidAmount, bidderId);
                }
            }
            else {
                throw new DatabaseException("Khong du so du de dat gia");
            }
        }
        catch (SQLException e) {
            System.err.println("Loi SQL o ham placeBid: " + e.getMessage());
            throw new DatabaseException("Loi he thong: khong the dat gia", e);
        }
    }

    /**
     * Lấy danh sách lịch sử đặt giá của một cuộc đấu giá
     */
    public List<BidTransaction> getBidsByAuctionId(int auctionId) throws DatabaseException {
        List<BidTransaction> list = new ArrayList<>();
        String query = "SELECT * FROM bids WHERE auction_id = ? ORDER BY bid_amount DESC";

        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, auctionId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                BidTransaction bid = new BidTransaction(
                        rs.getInt("bid_id"),
                        rs.getInt("auction_id"),
                        rs.getInt("bidder_id"),
                        rs.getDouble("bid_amount"),
                        rs.getTimestamp("bid_time").toLocalDateTime()
                );
                list.add(bid);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Không thể lấy lịch sử đấu giá.", e);
        }
        return list;
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}