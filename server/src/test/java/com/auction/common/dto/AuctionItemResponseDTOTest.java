package com.auction.common.dto;

import com.auction.common.dto.response.AuctionItemResponseDTO;
import com.auction.common.enums.ItemStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuctionItemResponseDTOTest {

    //Test Constructor 4 tham số và các hàm Getter
    @Test
    void testConstructorAndGetters() {
        String expectedAuctionId = "AUCTION 1";
        String expectedItemName = "Tranh Sơn Dầu";
        double expectedPrice = 3200.0;

        ItemStatus expectedStatus = ItemStatus.AVAILABLE;

        AuctionItemResponseDTO response = new AuctionItemResponseDTO(expectedAuctionId, expectedItemName, expectedPrice, expectedStatus);

        assertNotNull(response, "Lỗi: Không khởi tạo được đối tượng AuctionItemResponseDTO!");
        assertEquals(expectedAuctionId, response.getAuctionId(), "Lỗi: Khởi tạo auctionId hoặc hàm getAuctionId() bị sai!");
        assertEquals(expectedItemName, response.getItemName(), "Lỗi: Khởi tạo itemName hoặc hàm getItemName() bị sai!");
    }

    //Test các hàm Setter
    @Test
    void testSetters() {
        AuctionItemResponseDTO response = new AuctionItemResponseDTO("OLD_ID", "Tên Cũ", 100.0, ItemStatus.SOLD);

        String newAuctionId = "AUCTION 1000";
        String newItemName = "Bình Hoa Gốm Sứ Bát Tràng";

        response.setAuctionId(newAuctionId);
        response.setItemName(newItemName);

        assertEquals(newAuctionId, response.getAuctionId(), "Lỗi: Hàm setAuctionId() hoạt động không chính xác!");
        assertEquals(newItemName, response.getItemName(), "Lỗi: Hàm setItemName() hoạt động không chính xác!");
    }
}