package com.auction.common.dto;

import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.model.Auction.BidTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetBidInfoResponseDTOTest {

    //Test Constructor mặc định
    @Test
    void testDefaultConstructor() {
        GetBidInfoResponseDTO response = new GetBidInfoResponseDTO();

        assertNull(response.getBidTransactionList(), "Lỗi: Constructor mặc định nhưng danh sách giao dịch không phải null!");
        assertNull(response.getAuthStatus(), "Lỗi: Constructor mặc định nhưng authStatus không phải null!");
        assertNull(response.getMessage(), "Lỗi: Constructor mặc định nhưng message không phải null!");
    }

    //Test Constructor 1 tham số nạp danh sách giao dịch đặt giá
    @Test
    void testConstructorWithListParameters() {
        List<BidTransaction> mockList = new ArrayList<>();
        mockList.add(new BidTransaction(101, 1, 2, 500.0, LocalDateTime.now()));
        mockList.add(new BidTransaction(102, 1, 3, 600.0, LocalDateTime.now()));

        GetBidInfoResponseDTO response = new GetBidInfoResponseDTO(mockList);

        assertNotNull(response.getBidTransactionList(), "Lỗi: Danh sách bốc ra bị null!");
        assertEquals(2, response.getBidTransactionList().size(), "Lỗi: Số lượng phần tử trong danh sách không khớp!");
        assertEquals(500.0, response.getBidTransactionList().get(0).getBidAmount(), "Lỗi: Dữ liệu giao dịch đầu tiên bị sai lệch!");

        assertNull(response.getAuthStatus());
        assertNull(response.getMessage());
    }

    //Test toàn bộ tất cả các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        GetBidInfoResponseDTO response = new GetBidInfoResponseDTO();

        List<BidTransaction> mockList = new ArrayList<>();
        mockList.add(new BidTransaction(999, 5, 10, 1500.0, LocalDateTime.now()));

        String testMessage = "Get bidInfo successfully!";
        AuthStatus testStatus = AuthStatus.SUCCESS;

        response.setBidTransactionList(mockList);
        response.setMessage(testMessage);
        response.setAuthStatus(testStatus);

        assertEquals(mockList, response.getBidTransactionList(), "Lỗi: Hàm setBidTransactionList hoặc getBidTransactionList chạy sai!");
        assertEquals(testMessage, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage chạy sai!");
        assertEquals(testStatus, response.getAuthStatus(), "Lỗi: Hàm setAuthStatus hoặc getAuthStatus chạy sai!");
    }
}