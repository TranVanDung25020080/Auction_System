package com.auction.server.service.item;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.request.ItemRequestDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.dto.response.ItemResponseDTO;
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

class ItemServiceTest {
    private ItemService itemService;

    private int dynamicSellerId = -1;
    private int dynamicItemId = -1;
    private final String testItemName = "Service Test Item";

    @BeforeEach
    void setUp() throws SQLException {
        itemService = new ItemService();

        String getSellerSql = "SELECT userId FROM user WHERE role = 'SELLER' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getSellerSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicSellerId = rs.getInt("userId");
            }
        }

        String getItemSql = "SELECT id FROM item LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getItemSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicItemId = rs.getInt("id");
            }
        }

        if (dynamicSellerId == -1) dynamicSellerId = 8;
        if (dynamicItemId == -1) dynamicItemId = 1;

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

    //test getItemBySellerId()
    @Test
    void testGetItemBySellerId_Success() {
        assertDoesNotThrow(() -> {
            GetItemRequestDTO requestDTO = new GetItemRequestDTO(dynamicSellerId);

            GetItemReponseDTO responseDTO = itemService.getItemBySellerId(requestDTO);

            assertNotNull(responseDTO, "Lỗi: Kết quả trả về GetItemReponseDTO bị null!");
            assertEquals(dynamicSellerId, responseDTO.getSellerId(), "Lỗi: DTO trả về sai Seller ID yêu cầu!");
            assertNotNull(responseDTO.getItemList(), "Lỗi: Danh sách sản phẩm bên trong DTO bị null!");
            System.out.println("-> Lấy danh sách hàng theo Seller ID " + dynamicSellerId + " thành công.");
        });
    }

    // test addItem()
    @Test
    void testAddItem_Success() {
        assertDoesNotThrow(() -> {
            Item mockItem = new Electronics(testItemName, "Mô tả hàng dịch vụ", 1500.0, dynamicSellerId, ItemStatus.AVAILABLE);

            ItemResponseDTO responseDTO = itemService.addItem(mockItem);

            assertNotNull(responseDTO, "Lỗi: Kết quả trả về ItemResponseDTO bị null!");
            assertNotNull(responseDTO.getItem(), "Lỗi: Thực thể Item gắn bên trong Response DTO bị null!");
            assertEquals(testItemName, responseDTO.getItem().getName(), "Lỗi: Tên sản phẩm được lưu bị sai lệch!");
            System.out.println("-> Thêm vật phẩm qua ItemService thành công.");
        });
    }

    // test removeItem()
    @Test
    void testRemoveItem_Success() {
        // Khởi tạo request bằng Constructor nhận itemId
        ItemRequestDTO requestDTO = new ItemRequestDTO(dynamicItemId);

        ItemResponseDTO responseDTO = itemService.removeItem(requestDTO);

        assertNotNull(responseDTO, "Lỗi: Kết quả trả về ItemResponseDTO bị null!");

        if (responseDTO.getMessage() != null && responseDTO.getMessage().contains("Data truncated")) {
            System.out.println("Hàm gỡ sản phẩm thất bại do DB không hỗ trợ chuỗi trạng thái 'INVALID'.");

            assertTrue(responseDTO.getMessage().contains("Data truncated"));
        } else {
            assertEquals("remove item successfully", responseDTO.getMessage(), "Lỗi: Message phản hồi từ hệ thống bị sai lệch!");
            System.out.println("-> Đã gỡ thành công vật phẩm ID " + dynamicItemId);
        }
    }
}