package com.auction.server.dao;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuctionDAOTest {
    private AuctionDAO auctionDAO;

    private final String testItemName = "TRAN DANG";

    private int dynamicItemId = -1;
    private int dynamicSellerId = -1;
    private int dynamicBidderId = -1;

    @BeforeEach
    void setUp() throws SQLException {
        auctionDAO = new AuctionDAO();

        String getItemSql = "SELECT id FROM item LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getItemSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicItemId = rs.getInt("id");
            }
        }

        String getSellerSql = "SELECT userId FROM user WHERE role = 'SELLER' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getSellerSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicSellerId = rs.getInt("userId");
            }
        }

        String getBidderSql = "SELECT userId FROM user WHERE role = 'BIDDER' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getBidderSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicBidderId = rs.getInt("userId");
            }
        }

        if (dynamicItemId == -1) dynamicItemId = 6;
        if (dynamicSellerId == -1) dynamicSellerId = 8;
        if (dynamicBidderId == -1) dynamicBidderId = 9;

        cleanTestAuctionsSafe();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanTestAuctionsSafe(); //xóa sạch dữ liệu test ngay sau khi hàm test chạy xong
    }

    /**
     * Hàm trợ giúp: Xóa sạch dữ liệu test dựa trên tên độc bản an toàn
     */
    private void cleanTestAuctionsSafe() throws SQLException {
        String disableFk = "SET FOREIGN_KEY_CHECKS = 0";
        String deleteSql = "DELETE FROM auction WHERE item_name = ?";
        String enableFk = "SET FOREIGN_KEY_CHECKS = 1";

        try (Connection conn = MyDatabaseConfig.getConnection()) {
            try (PreparedStatement pstDisable = conn.prepareStatement(disableFk)) {
                pstDisable.execute();
            }
            try (PreparedStatement pstDelete = conn.prepareStatement(deleteSql)) {
                pstDelete.setString(1, testItemName);
                pstDelete.executeUpdate();
            }
            try (PreparedStatement pstEnable = conn.prepareStatement(enableFk)) {
                pstEnable.execute();
            }
        }
    }

    // Test createAuction()
    @Test
    void testCreateAuction_Success() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(3);

        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, startTime, endTime);
        }, "Lỗi: Khởi tạo phiên đấu giá bị hệ thống báo lỗi khóa ngoại hoặc cấu trúc!");
    }

    // test updateCurrentPrice()
    @Test
    void testUpdateCurrentPrice_Success() {
        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

            int generatedAuctionId = -1;
            String findIdSql = "SELECT auctionId FROM auction WHERE item_name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedAuctionId = rs.getInt("auctionId");
                    }
                }
            }

            if (generatedAuctionId != -1) {
                double newPrice = 650.0;
                auctionDAO.updateCurrentPrice(generatedAuctionId, newPrice, dynamicBidderId);

                Auction auction = auctionDAO.getAuctionInfoById(generatedAuctionId);
                if (auction != null) {
                    assertEquals(newPrice, auction.getCurrentHighestPrice(), "Lỗi: Giá đấu cao nhất không cập nhật đúng!");
                }
            }
        });
    }

    // Test getAuctionInfoById()
    @Test
    void testGetAuctionInfoById_Success() {
        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

            int generatedAuctionId = -1;
            String findIdSql = "SELECT auctionId FROM auction WHERE item_name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedAuctionId = rs.getInt("auctionId");
                    }
                }
            }

            if (generatedAuctionId != -1) {
                Auction auction = auctionDAO.getAuctionInfoById(generatedAuctionId);
                assertNotNull(auction, "Lỗi: Không tìm thấy thông tin phiên đấu giá!");
                assertEquals(testItemName, auction.getItemName());
            }
        });
    }

    // test getAuctionBySellerId()
    @Test
    void testGetAuctionBySellerId_Success() {
        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

            List<Auction> sellerAuctions = auctionDAO.getAuctionBySellerId(dynamicSellerId);
            assertNotNull(sellerAuctions, "Lỗi: Danh sách trả về bị null!");

            boolean foundOurTestAuction = false;
            for (Auction auc : sellerAuctions) {
                if (testItemName.equals(auc.getItemName())) {
                    foundOurTestAuction = true;
                    assertEquals(dynamicSellerId, auc.getSellerId(), "Lỗi: Sai thông tin id người bán!");
                }
            }
            assertTrue(foundOurTestAuction, "Lỗi: Không tìm thấy phiên test vừa thêm của người bán!");
        });
    }

    // test getAllAuction()
    @Test
    void testGetAllAuction_Success() {
        assertDoesNotThrow(() -> {
            List<Auction> allAuctions = auctionDAO.getAllAuction();
            assertNotNull(allAuctions, "Lỗi: Toàn bộ danh sách phiên đấu giá bị null!");
        });
    }

    // Test updateAuctionStatus()
    @Test
    void testUpdateAuctionStatus_Success() {
        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

            int generatedAuctionId = -1;
            String findIdSql = "SELECT auctionId FROM auction WHERE item_name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedAuctionId = rs.getInt("auctionId");
                    }
                }
            }

            if (generatedAuctionId != -1) {
                auctionDAO.updateAuctionStatus(AuctionStatus.FINISHED, generatedAuctionId);

                Auction currentAuction = auctionDAO.getAuctionInfoById(generatedAuctionId);
                if (currentAuction != null) {
                    assertEquals(AuctionStatus.FINISHED, currentAuction.getStatus(), "Lỗi: Chưa chuyển sang trạng thái mong muốn!");
                }
            }
        });
    }

    //test endAuction()
    @Test
    void testEndAuction_Success() {
        assertDoesNotThrow(() -> {
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now().plusDays(1);
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, startTime, endTime);

            int generatedAuctionId = -1;
            String findIdSql = "SELECT auctionId FROM auction WHERE item_name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedAuctionId = rs.getInt("auctionId");
                    }
                }
            }

            assertTrue(generatedAuctionId != -1, "Lỗi mồi dữ liệu: Không lấy được auctionId của phiên test!");

            double highestPrice = 750.0;
            auctionDAO.updateCurrentPrice(generatedAuctionId, highestPrice, dynamicBidderId);

            double initialBidderBalance = 0;
            double initialSellerBalance = 0;

            String balanceSql = "SELECT userId, balance FROM user WHERE userId IN (?, ?)";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(balanceSql)) {
                pst.setInt(1, dynamicBidderId);
                pst.setInt(2, dynamicSellerId);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int uId = rs.getInt("userId");
                        if (uId == dynamicBidderId) initialBidderBalance = rs.getDouble("balance");
                        if (uId == dynamicSellerId) initialSellerBalance = rs.getDouble("balance");
                    }
                }
            }

            auctionDAO.endAuction(generatedAuctionId);


            Auction closedAuction = auctionDAO.getAuctionInfoById(generatedAuctionId);
            assertNotNull(closedAuction, "Lỗi: Phiên đấu giá biến mất sau khi đóng!");
            assertEquals(AuctionStatus.FINISHED, closedAuction.getStatus(), "Lỗi: Phiên đấu giá chưa chuyển về trạng thái FINISHED!");

            String checkUserSql = "SELECT userId, balance FROM user WHERE userId IN (?, ?)";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(checkUserSql)) {
                pst.setInt(1, dynamicBidderId);
                pst.setInt(2, dynamicSellerId);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int uId = rs.getInt("userId");
                        double currentBalance = rs.getDouble("balance");

                        if (uId == dynamicBidderId) {
                            assertEquals(initialBidderBalance - highestPrice, currentBalance, 0.1,
                                    "Lỗi: Người mua (Bidder) chưa bị trừ đúng số tiền thắng đấu giá!");
                        }
                        if (uId == dynamicSellerId) {
                            assertEquals(initialSellerBalance + highestPrice, currentBalance, 0.1,
                                    "Lỗi: Người bán (Seller) chưa nhận được số tiền từ phiên đấu giá!");
                        }
                    }
                }
            }

            String checkItemSql = "SELECT item_status FROM item WHERE id = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(checkItemSql)) {
                pst.setInt(1, dynamicItemId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        assertEquals("SOLD", rs.getString("item_status"),
                                "Lỗi: Sản phẩm trong phiên đấu giá chưa được cập nhật trạng thái thành SOLD!");
                    }
                }
            }
        });
    }

    // Test extendEndTime()
    @Test
    void testExtendEndTime_Success() {
        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

            int generatedAuctionId = -1;
            String findIdSql = "SELECT auctionId FROM auction WHERE item_name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedAuctionId = rs.getInt("auctionId");
                    }
                }
            }

            if (generatedAuctionId != -1) {
                Auction before = auctionDAO.getAuctionInfoById(generatedAuctionId);

                auctionDAO.extendEndTime(generatedAuctionId, 450); // Gia hạn thêm 450 giây

                Auction after = auctionDAO.getAuctionInfoById(generatedAuctionId);
                if (before != null && after != null) {
                    assertTrue(after.getEndTime().isAfter(before.getEndTime()), "Lỗi: Hàm cộng dồn thời gian không hoạt động chính xác!");
                }
            }
        });
    }
}