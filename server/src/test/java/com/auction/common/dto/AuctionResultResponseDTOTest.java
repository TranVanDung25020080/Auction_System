package com.auction.common.dto;

import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ReponseType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuctionResultResponseDTOTest {

    //Test Constructor 4 tham số
    @Test
    void testConstructorWithFourParameters() {
        int expectedAuctionId = 101;
        int expectedWinnerId = 7;
        double expectedFinalPrice = 2500.0;
        AuctionStatus expectedStatus = AuctionStatus.FINISHED;

        AuctionResultResponseDTO response = new AuctionResultResponseDTO(expectedAuctionId, expectedWinnerId, expectedFinalPrice, expectedStatus);

        assertEquals(ReponseType.AUCTION_RESULT, response.getReponseType(), "Lỗi: Constructor chưa tự gán responseType thành AUCTION_RESULT!");

        assertEquals(expectedAuctionId, response.getAuctionId(), "Lỗi: Khởi tạo auctionId bị sai!");
        assertEquals(expectedWinnerId, response.getWinnerName(), "Lỗi: Hàm getWinnerName() không trả về đúng winnerId!");
        assertEquals(expectedFinalPrice, response.getFinalPrice(), "Lỗi: Khởi tạo finalPrice bị sai!");
        assertEquals(expectedStatus, response.getStatus(), "Lỗi: Khởi tạo status bị sai!");
    }

    //Test Constructor 1 tham số(Truyền người thắng)
    @Test
    void testConstructorWithWinnerIdOnly() {
        int expectedWinnerId = 9;

        AuctionResultResponseDTO response = new AuctionResultResponseDTO(expectedWinnerId);

        assertEquals(ReponseType.AUCTION_RESULT, response.getReponseType());

        assertEquals(expectedWinnerId, response.getWinnerName(), "Lỗi: Khởi tạo winnerId qua constructor 1 tham số bị sai!");
        assertEquals(0, response.getAuctionId(), "Lỗi: Mặc định ban đầu auctionId phải bằng 0!");
        assertEquals(0.0, response.getFinalPrice(), "Lỗi: Mặc định ban đầu finalPrice phải bằng 0.0!");
        assertNull(response.getStatus(), "Lỗi: Mặc định ban đầu status phải là null!");
    }

    //Test hàm Setter setStatus
    @Test
    void testSetStatus() {
        AuctionResultResponseDTO response = new AuctionResultResponseDTO(5);

        response.setStatus(AuctionStatus.FINISHED);

        assertEquals(AuctionStatus.FINISHED, response.getStatus(), "Lỗi: Hàm setStatus hoặc getStatus hoạt động không chính xác!");
    }

    //Test hàm hiển thị thông báo kết quả displayMessage()
    @Test
    void testDisplayMessage() {
        int testWinnerId = 88;
        AuctionResultResponseDTO response = new AuctionResultResponseDTO(testWinnerId);

        String expectedMessage = "User 88 has won";
        String actualMessage = response.displayMessage();

        assertNotNull(actualMessage, "Lỗi: Hàm displayMessage() trả về chuỗi null!");
        assertEquals(expectedMessage, actualMessage, "Lỗi: Chuỗi thông báo người thắng cuộc sinh ra bị sai định dạng!");
    }
}