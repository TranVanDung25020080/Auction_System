package com.auction.common.dto;

import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ReponseType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JoinRoomResponseDTOTest {

    //Test Constructor 2 tham số và việc tự động gán ReponseType
    @Test
    void testConstructorAndDefaultValues() {
        int expectedUserId = 10;
        int expectedRoomId = 99;

        JoinRoomResponseDTO response = new JoinRoomResponseDTO(expectedUserId, expectedRoomId);

        assertEquals(expectedUserId, response.getUserId(), "Lỗi: Khởi tạo userId bị sai hoặc getter trả về sai!");

        assertEquals(ReponseType.JOIN_ROOM, response.getReponseType(), "Lỗi: Constructor chưa tự gán responseType thành JOIN_ROOM!");

        assertNull(response.getAuctionStatus(), "Lỗi: Mặc định ban đầu auctionStatus phải là null!");
    }

    //Test các hàm Setter và Getter thực tế đang có
    @Test
    void testSettersAndGetters() {
        JoinRoomResponseDTO response = new JoinRoomResponseDTO(1, 100);

        response.setUserId(5);
        response.setAuctionStatus(AuctionStatus.OPEN);

        assertEquals(5, response.getUserId(), "Lỗi: Hàm setUserId hoặc getUserId hoạt động không chính xác!");
        assertEquals(AuctionStatus.OPEN, response.getAuctionStatus(), "Lỗi: Hàm setAuctionStatus hoặc getAuctionStatus hoạt động không chính xác!");
    }

    //Test hàm displayMessage() ghi đè (Override) từ BaseResponse
    @Test
    void testDisplayMessage() {
        int testUserId = 123;
        int testRoomId = 456;

        JoinRoomResponseDTO response = new JoinRoomResponseDTO(testUserId, testRoomId);

        String expectedMessage = "User 123 has joined room 456";
        String actualMessage = response.displayMessage();

        assertNotNull(actualMessage, "Lỗi: Hàm displayMessage() trả về chuỗi bị null!");
        assertEquals(expectedMessage, actualMessage, "Lỗi: Chuỗi thông báo tham gia phòng đấu giá sinh ra không đúng định dạng!");
    }
}