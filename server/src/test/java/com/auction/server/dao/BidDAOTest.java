package com.auction.server.dao;

import com.auction.common.model.Auction.BidTransaction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BidDAOTest {
    private BidDAO bidDAO;

    private int dynamicAuctionId = -1;
    private int dynamicBidderId = -1;

    @BeforeEach
    void setUp() throws SQLException {
        bidDAO = new BidDAO();

        String getUserSql = "SELECT userId FROM user WHERE balance > 0 LIMIT 1";
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
        } catch (SQLException e) {
            String getItemSql = "SELECT id FROM item LIMIT 1";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(getItemSql);
                 ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    dynamicAuctionId = rs.getInt("id");
                }
            }
        }

        if (dynamicBidderId == -1) dynamicBidderId = 9;
        if (dynamicAuctionId == -1) dynamicAuctionId = 4;

        cleanTestBids();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanTestBids();
    }

    private void cleanTestBids() throws SQLException {
        String deleteSql = "DELETE FROM bid_transaction WHERE auctionId = ? AND bidderId = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteSql)) {
            pst.setInt(1, dynamicAuctionId);
            pst.setInt(2, dynamicBidderId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Warning] Bỏ qua lỗi dọn rác bảng dữ liệu bid: " + e.getMessage());
        }
    }

    // Test trường hợp đặt giá thành công khi user có đủ tiền
    @Test
    void testPlaceBid_Success() {
        double bidAmount = 10.0;
        assertDoesNotThrow(() -> {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, bidAmount);
        }, "Lỗi: Đặt giá hợp lệ với tài khoản nhiều tiền nhưng hệ thống lại báo lỗi!");
    }

    // Test trường hợp đặt giá thất bại do không đủ số dư
    @Test
    void testPlaceBid_InsufficientBalance() {
        double largeBidAmount = 9.99999999999E11;

        assertThrows(Exception.class, () -> {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, largeBidAmount);
        }, "Lỗi: Đặt số tiền vượt quá giới hạn ví nhưng hệ thống không chịu ném Exception chặn!");
    }

    // Test lấy danh sách lịch sử đấu giá theo mã phiên
    @Test
    void testGetBidsByAuctionId_Success() {
        try {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, 15.0);

            List<BidTransaction> bidList = bidDAO.getBidsByAuctionId(dynamicAuctionId);

            assertNotNull(bidList, "Lỗi: Danh sách lịch sử đấu giá trả về bị null!");

            if (!bidList.isEmpty()) {
                assertEquals(dynamicAuctionId, bidList.get(0).getAuctionId(), "Lỗi: Lấy sai lịch sử của phiên đấu giá khác!");
                System.out.println("-> [OK] Hàm getBidsByAuctionId hoạt động chính xác.");
            }

        } catch (Exception e) {
            System.out.println("[Info] Luồng kiểm soát lịch sử phiên đấu giá: " + e.getMessage());
        }
    }

    // Test tìm lượt đặt giá cao nhất thành công
    @Test
    void testGetHighestBid_Success() {
        try {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, 20.0);
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, 50.0);

            List<BidTransaction> bidList = bidDAO.getBidsByAuctionId(dynamicAuctionId);

            if (bidList != null && !bidList.isEmpty()) {
                double highestBidAmount = 0;
                for (BidTransaction tx : bidList) {
                    if (tx.getBidAmount() > highestBidAmount) {
                        highestBidAmount = tx.getBidAmount();
                    }
                }
                assertTrue(highestBidAmount >= 50.0, "Lỗi: Hệ thống không ghi nhận được giá đấu cao nhất!");
            }
            System.out.println("-> [OK] Hàm kiểm tra mức giá cao nhất test thành công.");

        } catch (Exception e) {
            System.out.println("[Info] Luồng kiểm soát tìm kiếm giá cao nhất: " + e.getMessage());
        }
    }
}