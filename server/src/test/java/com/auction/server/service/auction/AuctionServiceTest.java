package com.auction.server.service.auction;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.request.GetAuctionRequestDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.model.Auction.Auction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

class AuctionServiceTest {
    private AuctionService auctionService;

    @BeforeEach
    void setUp() {
        auctionService = new AuctionService();
    }

    @Test
    void testGetAuction_DynamicData() {
        assertDoesNotThrow(() -> {
            List<Auction> activeAuctions = auctionService.getAllAuctions();

            if (activeAuctions == null || activeAuctions.isEmpty()) {
                System.out.println("DB Railway trống phòng, bỏ qua kiểm thử thông tin chi tiết.");
                return;
            }

            Auction sampleAuction = activeAuctions.get(0);
            int dynamicAuctionId = sampleAuction.getAuctionId();

            Auction result = auctionService.getAuction(dynamicAuctionId);
            assertNotNull(result, "Lỗi: Không lấy được thông tin chi tiết của phòng tồn tại thực tế!");
            assertEquals(dynamicAuctionId, result.getAuctionId());
        });
    }

    @Test
    void testGetAllAuctionMap_StructureCheck() {
        assertDoesNotThrow(() -> {
            Map<Integer, Auction> auctionMap = auctionService.getAllAuction();
            assertNotNull(auctionMap, "Lỗi: Hệ thống trả về Map rỗng!");

            if (!auctionMap.isEmpty()) {
                Integer firstKey = auctionMap.keySet().iterator().next();
                assertEquals(firstKey, auctionMap.get(firstKey).getAuctionId());
            }
        });
    }

    @Test
    void testGetAuctionBySellerId_DynamicCheck() {
        assertDoesNotThrow(() -> {
            List<Auction> activeAuctions = auctionService.getAllAuctions();
            if (activeAuctions == null || activeAuctions.isEmpty()) return;

            int dynamicSellerId = activeAuctions.get(0).getSellerId();

            GetAuctionRequestDTO requestDTO = new GetAuctionRequestDTO();
            requestDTO.setSellerId(dynamicSellerId);

            GetAuctionResponseDTO responseDTO = auctionService.getAuctionBySellerId(requestDTO);
            assertNotNull(responseDTO);
            assertNotNull(responseDTO.getAuctionList());
            assertTrue(responseDTO.getAuctionList().size() > 0);
        });
    }
}