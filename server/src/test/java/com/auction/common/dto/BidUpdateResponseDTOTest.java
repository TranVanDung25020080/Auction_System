package com.auction.common.dto;

import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.enums.ReponseType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BidUpdateResponseDTOTest {

    //Test Constructor mặc định không tham số
    @Test
    void testDefaultConstructor() {
        BidUpdateResponseDTO response = new BidUpdateResponseDTO();

        assertEquals(ReponseType.BID_UPDATE, response.getReponseType(), "Lỗi: Constructor mặc định chưa tự gán responseType!");

        assertEquals(0, response.getAuctionId());
        assertEquals(0, response.getBidderId());
        assertEquals(0.0, response.getNewHighestPrice());
        assertNull(response.getHighestBidderName());
        assertNull(response.getTimeStamp());
        assertNull(response.getBidStatus());
        assertNull(response.getEndTime());
    }

    // Test Constructor 4 tham số
    @Test
    void testConstructorWithFourParameters() {
        int testAuctionId = 101;
        double testPrice = 1500.0;
        String testBidderName = "Daddy Tam";
        String testTimeStr = "23:15:00";

        BidUpdateResponseDTO response = new BidUpdateResponseDTO(testAuctionId, testPrice, testBidderName, testTimeStr);

        assertEquals(ReponseType.BID_UPDATE, response.getReponseType());
        assertEquals(testAuctionId, response.getAuctionId(), "Lỗi: Constructor 4 tham số gán sai auctionId!");
        assertEquals(testPrice, response.getNewHighestPrice(), "Lỗi: Constructor 4 tham số gán sai newHighestPrice!");
        assertEquals(testBidderName, response.getHighestBidderName(), "Lỗi: Constructor 4 tham số gán sai highestBidderName!");
        assertEquals(testTimeStr, response.getTimeStamp(), "Lỗi: Constructor 4 tham số gán sai timeStamp!");
    }

    //Test Constructor 3 tham số
    @Test
    void testConstructorWithThreeParameters() {
        int testAuctionId = 202;
        int testBidderId = 7;
        double testPrice = 850.50;

        BidUpdateResponseDTO response = new BidUpdateResponseDTO(testAuctionId, testBidderId, testPrice);

        assertEquals(ReponseType.BID_UPDATE, response.getReponseType());
        assertEquals(testAuctionId, response.getAuctionId(), "Lỗi: Constructor 3 tham số gán sai auctionId!");
        assertEquals(testBidderId, response.getBidderId(), "Lỗi: Constructor 3 tham số gán sai bidderId!");
        assertEquals(testPrice, response.getNewHighestPrice(), "Lỗi: Constructor 3 tham số gán sai newHighestPrice!");
    }

    //Test toàn bộ các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        BidUpdateResponseDTO response = new BidUpdateResponseDTO();

        int newAuctionId = 55;
        int newBidderId = 12;
        double newPrice = 2100.0;
        BidStatus status = BidStatus.SUCCESS;
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);

        response.setAuctionId(newAuctionId);
        response.setBidderId(newBidderId);
        response.setNewHighestPrice(newPrice);
        response.setBidStatus(status);
        response.setEndTime(endTime);

        assertEquals(newAuctionId, response.getAuctionId(), "Lỗi: Hàm setAuctionId hoặc getAuctionId chạy sai!");
        assertEquals(newBidderId, response.getBidderId(), "Lỗi: Hàm setBidderId hoặc getBidderId chạy sai!");
        assertEquals(newPrice, response.getNewHighestPrice(), "Lỗi: Hàm setNewHighestPrice hoặc getNewHighestPrice chạy sai!");
        assertEquals(status, response.getBidStatus(), "Lỗi: Hàm setBidStatus hoặc getBidStatus chạy sai!");
        assertEquals(endTime, response.getEndTime(), "Lỗi: Hàm setEndTime hoặc getEndTime chạy sai!");
    }

    //Test hàm displayMessage() được Override từ BaseResponse
    @Test
    void testDisplayMessage() {
        BidUpdateResponseDTO response = new BidUpdateResponseDTO();
        response.setBidderId(9);
        response.setNewHighestPrice(720.0);

        String expectedMessage = "User 9 has bidded 720.0";
        String actualMessage = response.displayMessage();

        assertNotNull(actualMessage, "Lỗi: Hàm displayMessage() trả về chuỗi null!");
        assertEquals(expectedMessage, actualMessage, "Lỗi: Chuỗi thông báo đặt giá sinh ra bị sai định dạng!");
    }
}