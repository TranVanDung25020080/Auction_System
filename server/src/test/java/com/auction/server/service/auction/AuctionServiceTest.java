package com.auction.server.service.auction;

import static org.junit.jupiter.api.Assertions.*;
import com.auction.common.model.Auction.Auction;
import com.auction.server.exception.DatabaseException;
import com.auction.server.auction.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Map;

class AuctionServiceTest {
    private AuctionService auctionService;

    @BeforeEach
    void setUp() {
        auctionService = new AuctionService();
    }

    @Test
    //Kiểm tra Lấy tất cả các phiên đấu giá không trống
    void testGetAllAuctions_NotEmpty() throws SQLException {
        // Kiểm tra xem database có danh sách đấu giá không
        var auctions = auctionService.getAllAuctions();
        assertNotNull(auctions);
        assertFalse(auctions.isEmpty(), "Danh sách đấu giá không được trống");
    }

    @Test
    //Kiểm tra Lấy đấu giá theo ID hợp lệ
    void testGetAuctionById_ValidId() throws DatabaseException {
        int validId = 1;
        Auction auction = auctionService.getAuction(validId);

        assertNotNull(auction);
        assertEquals(validId, auction.getAuctionId());
        System.out.println("Món hàng đang đấu giá: " + auction.getItemName());
    }

    @Test
    //Kiểm tra việc lấy thông tin đấu giá bằng ID không hợp lệ
    void testGetAuctionById_InvalidId() throws DatabaseException {
        int fakeId = 999999;
        Auction auction = auctionService.getAuction(fakeId);
        assertNull(auction, "Nếu ID không tồn tại phải trả về null");
    }

    @Test
    //Kiểm tra tất cả bản đồ đấu giá
    void testGetAllAuctionMap() throws SQLException {
        Map<Integer, Auction> auctionMap = auctionService.getAllAuction();
        assertNotNull(auctionMap);
        // Kiểm tra xem Key của Map có khớp với AuctionId Sbên trong không
        if (!auctionMap.isEmpty()) {
            Integer firstKey = auctionMap.keySet().iterator().next();
            assertEquals(firstKey, auctionMap.get(firstKey).getAuctionId());
        }
    }
}