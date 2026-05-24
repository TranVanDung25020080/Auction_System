package com.auction.common.dto;

import com.auction.common.dto.response.CreateAuctionResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CreateAuctionResponseDTOTest {

    //Test Constructor mặc định
    @Test
    void testDefaultConstructor() {
        CreateAuctionResponseDTO response = new CreateAuctionResponseDTO();

        assertNull(response.getAuction(), "Lỗi: Constructor mặc định nhưng đối tượng auction không phải null!");
        assertNull(response.getMessage(), "Lỗi: Constructor mặc định nhưng thông báo message không phải null!");
    }

    //Test Constructor 2 tham số
    @Test
    void testConstructorWithTwoParameters() {
        Auction mockAuction = new Auction(1, 10, 2, 500.0, 0, LocalDateTime.now(), LocalDateTime.now().plusDays(2), AuctionStatus.PENDING, "Tranh Phong Cảnh");
        String expectedMessage = "Tạo phiên đấu giá mới thành công!";

        CreateAuctionResponseDTO response = new CreateAuctionResponseDTO(mockAuction, expectedMessage);

        assertNotNull(response.getAuction(), "Lỗi: Đối tượng auction bốc ra bị null!");
        assertEquals("Tranh Phong Cảnh", response.getAuction().getItemName(), "Lỗi: Tên vật phẩm trong phiên đấu giá bị sai lệch!");
        assertEquals(expectedMessage, response.getMessage(), "Lỗi: Thông báo hệ thống gán vào bị sai lệch!");
    }

    //Test tất cả các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        CreateAuctionResponseDTO response = new CreateAuctionResponseDTO();

        Auction mockAuction = new Auction(2, 11, 2, 750.0, 0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), AuctionStatus.OPEN, "Đồng Hồ Cổ");
        String testMessage = "Hệ thống đã phê duyệt phiên đấu giá thành công!";

        response.setAuction(mockAuction);
        response.setMessage(testMessage);

        assertEquals(mockAuction, response.getAuction(), "Lỗi: Hàm setAuction hoặc getAuction chạy sai!");
        assertEquals("Đồng Hồ Cổ", response.getAuction().getItemName(), "Lỗi: Thuộc tính bên trong đối tượng auction bị sai lệch!");
        assertEquals(testMessage, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage chạy sai!");
    }
}