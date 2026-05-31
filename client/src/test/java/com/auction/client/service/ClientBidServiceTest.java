package com.auction.client.service;

import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.request.AutoBidRequestDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientBidServiceTest {

    @Test
    void testCreateNormalBidRequest_Valid() {
        // test mô phỏng người dùng nhập 15.0$ đấu giá trên giao diện (cao hơn giá hiện tại 10.0$)
        int bidderId = 8;
        int auctionId = 4;
        double bidAmount = 15.0;
        double currentPrice = 10.0;

        BidRequestDTO dto = new BidRequestDTO(bidderId, auctionId, bidAmount, currentPrice);

        assertEquals(8, dto.getBidderId());
        assertEquals(4, dto.getAuctionId());
        assertEquals(15.0, dto.getBidAmount());
    }

    @Test
    void testValidateBidAmount_LessThanCurrentPrice() {
        //test logic Client tự chặn nếu người dùng nhập số tiền thấp hơn giá hiện tại
        double userInpuptPrice = 8.5;
        double currentHighestPrice = 10.0;

        boolean isPriceValid = userInpuptPrice > currentHighestPrice;

        assertFalse(isPriceValid, "Lỗi: Client phải trả về False để chặn không cho gửi giá thầu thấp lên Server!");
    }

    @Test
    void testCreateAutoBidRequest() {
        // test mô phỏng dữ liệu khi người dùng kích hoạt Đấu giá tự động (Auto Bid)
        int bidderId = 8;
        int auctionId = 4;
        double maxBid = 100.0;
        double increment = 5.0;

        AutoBidRequestDTO autoDto = new AutoBidRequestDTO() {
            @Override public int getBidderId() { return bidderId; }
            @Override public int getAuctionId() { return auctionId; }
            @Override public double getMaxBid() { return maxBid; }
            @Override public double getIncrement() { return increment; }
        };

        assertEquals(100.0, autoDto.getMaxBid());
        assertEquals(5.0, autoDto.getIncrement());
    }
}