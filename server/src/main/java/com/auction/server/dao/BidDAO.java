package com.auction.server.dao;

import com.auction.common.model.Auction.BidTransaction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {
    public boolean placeBid(int auctionId, int bidderId, double bidAmount) throws DatabaseException {
        String insertBidQuery = "INSERT INTO bids (auction_id, bidder_id, bid_amount, bid_time) VALUES (?, ?, ?, NOW())";
        String updateUserQuery = "UPDATE users SET balance = balance - ? WHERE userId = ? AND balance >= ?";

        Connection conn = null;
        try {
            conn = MyDatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstUser = conn.prepareStatement(updateUserQuery)) {
                pstUser.setDouble(1, bidAmount);
                pstUser.setInt(2, bidderId);
                pstUser.setDouble(3, bidAmount);

                int affectedRows = pstUser.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Thất bại: Người dùng không tồn tại hoặc không đủ số dư!");
                }
            }

            try (PreparedStatement pstBid = conn.prepareStatement(insertBidQuery)) {
                pstBid.setInt(1, auctionId);
                pstBid.setInt(2, bidderId);
                pstBid.setDouble(3, bidAmount);
                pstBid.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Loi SQL tai BidDAO.placeBid: " + e.getMessage());
            throw new DatabaseException("Lỗi hệ thống khi đặt giá thầu.", e);
        } finally {
            closeConnection(conn);
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