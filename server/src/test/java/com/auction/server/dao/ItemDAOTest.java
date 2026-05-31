package com.auction.server.dao;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {
    private ItemDAO itemDAO;
    private final String testItemName = "mác búc pờ rồ 2026";
    private int dynamicSellerId = -1;

    @BeforeEach
    void setUp() throws SQLException {
        itemDAO = new ItemDAO();

        String getSellerSql = "SELECT userId FROM user WHERE role = 'SELLER' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getSellerSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicSellerId = rs.getInt("userId");
            }
        }

        if (dynamicSellerId == -1) {
            dynamicSellerId = 8;
        }

        cleanTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanTestData();
    }

    private void cleanTestData() throws SQLException {
        String disableFk = "SET FOREIGN_KEY_CHECKS = 0";
        String deleteSql = "DELETE FROM item WHERE name = ?";
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

    //Test phương thức addItem()
    @Test
    void testAddItem_Success() {
        Electronics laptop = new Electronics(testItemName, "Máy đẹp nguyên seal", 2000.0, dynamicSellerId, ItemStatus.AVAILABLE);
        assertDoesNotThrow(() -> {
            itemDAO.addItem(laptop);

            boolean isInserted = false;
            String checkSql = "SELECT id FROM item WHERE name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(checkSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        isInserted = true;
                        System.out.println("->Sản phẩm " + testItemName + " đã nằm trong DB với ID: " + rs.getInt("id"));
                    }
                }
            }
            assertTrue(isInserted, "Lỗi: Hàm không báo exception nhưng dữ liệu thực tế vẫn chưa được ghi vào DB!");

        }, "Lỗi: Thêm vật phẩm thất bại, hệ thống ném ngoại lệ!");
    }

    //Test phương thức getItemById()
    @Test
    void testGetItemById_Success() {
        assertDoesNotThrow(() -> {
            Electronics laptop = new Electronics(testItemName, "Kiểm thử tìm kiếm", 2000.0, dynamicSellerId, ItemStatus.AVAILABLE);
            itemDAO.addItem(laptop);

            int generatedId = -1;
            String findIdSql = "SELECT id FROM item WHERE name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedId = rs.getInt("id");
                    }
                }
            }

            if (generatedId != -1) {
                Item item = itemDAO.getItemById(generatedId);
                assertNotNull(item, "Lỗi: Đã thêm sản phẩm nhưng getItemById lại trả về null!");
                assertEquals(generatedId, item.getId(), "Lỗi: Lấy sai ID của sản phẩm cần tìm!");
                assertEquals(testItemName, item.getName(), "Lỗi: Tên sản phẩm lấy ra bị lệch!");
            }
        });
    }

    //test phương thức getAllItems()
    @Test
    void testGetAllItems_Success() {
        assertDoesNotThrow(() -> {
            List<Item> allItems = itemDAO.getAllItems();
            assertNotNull(allItems, "Lỗi: Danh sách sản phẩm trả về bị null!");
            System.out.println("Tổng số sản phẩm hiện tại trong DB: " + allItems.size());
        });
    }

    //Test phương thức getItemBySellerId()
    @Test
    void testGetItemBySellerId_Success() {
        assertDoesNotThrow(() -> {
            Electronics laptop = new Electronics(testItemName, "Kiểm thử theo Seller", 2000.0, dynamicSellerId, ItemStatus.AVAILABLE);
            itemDAO.addItem(laptop);

            List<Item> itemsOfSeller = itemDAO.getItemBySellerId(dynamicSellerId);
            assertNotNull(itemsOfSeller, "Lỗi: Kết quả danh sách trả về bị null!");
            System.out.println("Số lượng sản phẩm lọc được theo seller_id trong DB: " + itemsOfSeller.size());
        }, "Lỗi hệ thống: Hàm ném ra ngoại lệ không mong muốn khi truy vấn!");
    }

    //Test phương tức updateItemStatus()
    @Test
    void testUpdateItemStatus_Success() {
        assertDoesNotThrow(() -> {
            Electronics laptop = new Electronics(testItemName, "Kiểm thử trạng thái", 2000.0, dynamicSellerId, ItemStatus.AVAILABLE);
            itemDAO.addItem(laptop);

            int generatedId = -1;
            String findIdSql = "SELECT id FROM item WHERE name = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(findIdSql)) {
                pst.setString(1, testItemName);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        generatedId = rs.getInt("id");
                    }
                }
            }

            if (generatedId != -1) {
                ItemStatus newStatus = ItemStatus.SOLD;
                itemDAO.updateItemStatus(generatedId, newStatus);

                String checkStatusSql = "SELECT item_status FROM item WHERE id = ?";
                try (Connection conn = MyDatabaseConfig.getConnection();
                     PreparedStatement pst = conn.prepareStatement(checkStatusSql)) {
                    pst.setInt(1, generatedId);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            assertEquals(newStatus.toString(), rs.getString("item_status"), "Lỗi: Trạng thái vật phẩm trong DB chưa được cập nhật chính xác!");
                        }
                    }
                }
            }
        });
    }
}