package com.auction.common.dto;

import com.auction.common.dto.response.ItemResponseDTO;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemResponseDTOTest {

    //Test Constructor không tham số
    @Test
    void testDefaultConstructor() {
        ItemResponseDTO response = new ItemResponseDTO();

        assertNull(response.getItem(), "Lỗi: Khởi tạo mặc định nhưng item không phải null!");
        assertNull(response.getMessage(), "Lỗi: Khởi tạo mặc định nhưng message không phải null!");
    }

    //Test Constructor có đầy đủ 2 tham số (Item, Message)
    @Test
    void testConstructorWithParameters() {
        String targetName = "Iphone 16 promax";
        String description = "Mượt";
        double initialPrice = 1200.0;
        int sellerId = 2;
        ItemStatus status = ItemStatus.AVAILABLE;

        Item mockItem = new Electronics(targetName, description, initialPrice, sellerId, status);
        String expectedMessage = "Lấy thông tin sản phẩm thành công";

        ItemResponseDTO response = new ItemResponseDTO(mockItem, expectedMessage);

        assertNotNull(response.getItem(), "Lỗi: Đối tượng Item bị null sau khi nạp qua Constructor!");
        assertEquals("Iphone 16 promax", response.getItem().getName(), "Lỗi: Tên của vật phẩm bốc ra bị sai lệch!");
        assertEquals(expectedMessage, response.getMessage(), "Lỗi: Nội dung thông điệp message bị sai lệch!");
    }

    //Test tất cả các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        ItemResponseDTO response = new ItemResponseDTO();

        Item mockItem = new Electronics("Macbook Air", "Máy màu xám không gian", 999.0, 2, ItemStatus.AVAILABLE);
        String testMessage = "Sản phẩm đã được đấu giá thành công";

        response.setItem(mockItem);
        response.setMessage(testMessage);

        assertEquals(mockItem, response.getItem(), "Lỗi: Hàm setItem hoặc getItem hoạt động không chính xác!");
        assertEquals(testMessage, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage hoạt động không chính xác!");
    }
}