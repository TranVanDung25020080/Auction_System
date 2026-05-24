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

    // Kiểm thử tạo phiên đấu giá thành công
    @Test
    void testCreateAuction_Success() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(3);

        assertDoesNotThrow(() -> {
            auctionDAO.createAuction(dynamicItemId, testItemName, 500.0, dynamicSellerId, startTime, endTime);
        }, "Lỗi: Khởi tạo phiên đấu giá bị hệ thống báo lỗi khóa ngoại hoặc cấu trúc!");
    }

    // Kiểm thử cập nhật bước giá đấu cao nhất hiện tại
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

    // Kiểm thử hàm bốc thông tin phiên đấu giá theo ID
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

    // Kiểm thử lấy danh sách phiên đấu giá theo người bán
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

    // Kiểm thử hàm lấy toàn bộ danh sách phiên đấu giá hiện có
    @Test
    void testGetAllAuction_Success() {
        assertDoesNotThrow(() -> {
            List<Auction> allAuctions = auctionDAO.getAllAuction();
            assertNotNull(allAuctions, "Lỗi: Toàn bộ danh sách phiên đấu giá bị null!");
        });
    }

    // Kiểm thử hàm cập nhật trạng thái phiên
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

    // Kiểm thử hàm gia hạn thời gian kết thúc của phiên
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