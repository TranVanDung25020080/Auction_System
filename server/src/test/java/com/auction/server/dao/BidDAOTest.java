package com.auction.server.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.model.Auction.BidTransaction;
import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class BidDAOTest {

    private BidDAO bidDAO;
    private int dynamicBidderId = -1;
    private int dynamicAuctionId = -1;
    private final double fakeTestBidAmount = 999999.0;
    @BeforeEach
    void setUp() throws SQLException {
        bidDAO = new BidDAO();

        String getBidderSql = "SELECT userId FROM user WHERE role = 'BIDDER' ORDER BY balance DESC LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getBidderSql);
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
        assertTrue(dynamicAuctionId != -1, "LỖI KIỂM THỬ: Bảng 'auction' của bạn hiện đang không có dữ liệu rác nào. Hãy thêm ít nhất 1 dòng vào bảng 'auction' trước!");

        cleanTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanTestData();
    }

    private void cleanTestData() throws SQLException {
        String deleteSql = "DELETE FROM bid_transaction WHERE auctionId = ? AND bidAmount = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteSql)) {
            pst.setInt(1, dynamicAuctionId);
            pst.setDouble(2, fakeTestBidAmount);
            pst.executeUpdate();
        }
    }

    @Test
    void testPlaceBid_Success() {
        assertDoesNotThrow(() -> {
            double bidAmount = fakeTestBidAmount;
            double maxBidDuringAuction = bidAmount + 1000.0;

            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, bidAmount, maxBidDuringAuction);

            List<BidTransaction> bids = bidDAO.getBidInfoByBidderId(dynamicBidderId, dynamicAuctionId);
            boolean hasInserted = bids.stream().anyMatch(b -> b.getBidAmount() == bidAmount);

            assertTrue(hasInserted, "Lỗi: Đặt giá không thành công hoặc không tìm thấy dữ liệu dưới DB!");
        });
    }

    @Test
    void testPlaceBid_Failure_MaxBidTooLow() {
        double bidAmount = fakeTestBidAmount;
        double invalidMaxBid = bidAmount - 100.0;
        Exception exception = assertThrows(Exception.class, () -> {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, bidAmount, invalidMaxBid);
        });

        assertTrue(exception.getMessage().contains("Khong du so du de dat gia"),
                "Lỗi: Không trả về đúng thông báo lỗi mong muốn!");
    }

    @Test
    void testGetBidsByAuctionId_Success() {
        assertDoesNotThrow(() -> {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, fakeTestBidAmount, fakeTestBidAmount + 10.0);

            List<BidTransaction> bidsList = bidDAO.getBidsByAuctionId(dynamicAuctionId);

            assertNotNull(bidsList);
            assertFalse(bidsList.isEmpty(), "Lỗi: Danh sách lịch sử đấu giá trống!");
            assertEquals(dynamicAuctionId, bidsList.get(bidsList.size() - 1).getAuctionId());
        });
    }

    @Test
    void testGetBidInfoByBidderId_Success() {
        assertDoesNotThrow(() -> {
            bidDAO.placeBid(dynamicAuctionId, dynamicBidderId, fakeTestBidAmount, fakeTestBidAmount + 10.0);

            List<BidTransaction> bidderBids = bidDAO.getBidInfoByBidderId(dynamicBidderId, dynamicAuctionId);

            assertNotNull(bidderBids);
            assertFalse(bidderBids.isEmpty(), "Lỗi: Không tìm thấy giao dịch nào khớp với bidderId và auctionId!");
            assertEquals(dynamicBidderId, bidderBids.get(bidderBids.size() - 1).getBidderId());
        });
    }
}