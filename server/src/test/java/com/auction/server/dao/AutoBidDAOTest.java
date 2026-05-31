package com.auction.server.dao;

import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AutoBidDAOTest {
    private AutoBidDAO autoBidDAO;

    private int dynamicBidderId = -1;
    private int dynamicAuctionId = -1;

    @BeforeEach
    void setUp() throws SQLException {
        autoBidDAO = new AutoBidDAO();

        String getUserSql = "SELECT userId FROM user WHERE role = 'BIDDER' AND balance > 0 LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getUserSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicBidderId = rs.getInt("userId");
            }
        }

        String getAuctionSql = "SELECT auctionId FROM auction LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getAuctionSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicAuctionId = rs.getInt("auctionId");
            }
        }

        if (dynamicBidderId == -1) dynamicBidderId = 9;
        if (dynamicAuctionId == -1) dynamicAuctionId = 4;
        cleanTestAutoBids();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanTestAutoBids();
    }

    private void cleanTestAutoBids() throws SQLException {
        String deleteSql = "DELETE FROM auto_bid WHERE bidderId = ? AND auctionId = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteSql)) {
            pst.setInt(1, dynamicBidderId);
            pst.setInt(2, dynamicAuctionId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Warning] Bỏ qua nếu cấu trúc tên cột auto_bid có sự khác biệt: " + e.getMessage());
        }
    }

    // Test insertAutoBid()
    @Test
    void testInsertAutoBid_Success() {
        double maxBid = 2000.0;
        double increment = 50.0;

        assertDoesNotThrow(() -> {
            autoBidDAO.insertAutoBid(dynamicBidderId, dynamicAuctionId, maxBid, increment);
            System.out.println("-> [SUCCESS] Đã thiết lập AutoBid thành công cho bidder: "
                    + dynamicBidderId + " tại phiên: " + dynamicAuctionId);
        }, "Lỗi: Thiết lập tự động đấu giá hợp lệ nhưng hệ thống lại ném ngoại lệ!");
    }

    // Test updateAutoBid()
    @Test
    void testUpdateAutoBid_Success() {
        double initialMaxBid = 1500.0;
        double initialIncrement = 50.0;

        double newMaxBid = 2500.0;
        double newIncrement = 100.0;

        assertDoesNotThrow(() -> {
            autoBidDAO.insertAutoBid(dynamicBidderId, dynamicAuctionId, initialMaxBid, initialIncrement);

            autoBidDAO.updateAutoBid(dynamicBidderId, newMaxBid, newIncrement);

            System.out.println("-> [SUCCESS] Cập nhật cấu hình tự động đấu giá thành công.");
        }, "Lỗi hệ thống: Quá trình cập nhật cấu hình AutoBid bị ném ra ngoại lệ không mong muốn!");
    }
}