package com.auction.common.dto;

import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetAuctionResponseDTOTest {

    //Test Constructor mặc định không tham số
    @Test
    void testDefaultConstructor() {
        GetAuctionResponseDTO response = new GetAuctionResponseDTO();

        assertNull(response.getAuctionList(), "Lỗi: Constructor mặc định nhưng danh sách phiên đấu giá không phải null!");
        assertNull(response.getMessage(), "Lỗi: Constructor mặc định nhưng thông báo message không phải null!");
    }

    //Test Constructor nhận vào 1 đối tượng Auction duy nhất
    @Test
    void testConstructorWithSingleAuction() {
        Auction mockAuction = new Auction(1, 10, 2, 1500.0, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), AuctionStatus.OPEN, "Laptop Dell");

        GetAuctionResponseDTO response = new GetAuctionResponseDTO(mockAuction);

        assertNotNull(response, "Lỗi: Không khởi tạo được đối tượng DTO từ một phiên đấu giá lẻ!");
        assertNull(response.getAuctionList(), "Lỗi: Khởi tạo phiên lẻ nhưng danh sách auctionList lại bị gán dữ liệu!");
    }

    //Test Constructor nhận vào một danh sách List<Auction>
    @Test
    void testConstructorWithAuctionList() {
        List<Auction> mockList = new ArrayList<>();
        mockList.add(new Auction(1, 10, 2, 1500.0, 1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), AuctionStatus.OPEN, "Laptop Dell"));
        mockList.add(new Auction(2, 11, 2, 800.0, 0, LocalDateTime.now(), LocalDateTime.now().plusDays(2), AuctionStatus.PENDING, "Iphone 13"));

        GetAuctionResponseDTO response = new GetAuctionResponseDTO(mockList);

        assertNotNull(response.getAuctionList(), "Lỗi: Danh sách bốc từ DTO ra bị null!");
        assertEquals(2, response.getAuctionList().size(), "Lỗi: Số lượng phần tử trong danh sách phiên đấu giá không khớp!");
        assertEquals("Laptop Dell", response.getAuctionList().get(0).getItemName(), "Lỗi: Dữ liệu tên sản phẩm của phần tử đầu tiên bị sai!");
    }

    //Test tất cả các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        GetAuctionResponseDTO response = new GetAuctionResponseDTO();

        List<Auction> mockList = new ArrayList<>();
        mockList.add(new Auction(5, 20, 3, 350.0, 4, LocalDateTime.now(), LocalDateTime.now().plusHours(5), AuctionStatus.OPEN, "Bàn phím cơ"));
        String testMessage = "Lấy danh sách phiên đấu giá thành công!";

        response.setAuctionList(mockList);
        response.setMessage(testMessage);

        assertEquals(mockList, response.getAuctionList(), "Lỗi: Hàm setAuctionList hoặc getAuctionList hoạt động sai!");
        assertEquals(testMessage, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage hoạt động sai!");
    }
}