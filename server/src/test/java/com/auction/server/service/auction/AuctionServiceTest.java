package com.auction.server.service.auction;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.request.GetAuctionRequestDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class AuctionServiceTest {
    private AuctionService auctionService;

    private int dynamicAuctionId = -1;
    private int dynamicSellerId = -1;

    @BeforeEach
    void setUp() throws SQLException {
        auctionService = new AuctionService();

        String getAuctionSql = "SELECT auctionId, sellerId FROM auction LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getAuctionSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicAuctionId = rs.getInt("auctionId");
                dynamicSellerId = rs.getInt("sellerId");
            }
        }

        if (dynamicAuctionId == -1) dynamicAuctionId = 4;
        if (dynamicSellerId == -1) dynamicSellerId = 8;
    }

    // test getAllAuctions()
    @Test
    void testGetAllAuctions_Success() throws SQLException {
        List<Auction> auctions = auctionService.getAllAuctions();

        assertNotNull(auctions, "Lỗi: Danh sách đấu giá trả về bị null!");
        assertFalse(auctions.isEmpty(), "Lỗi: Hệ thống đang có dữ liệu thực tế nhưng hàm trả về danh sách trống!");
        System.out.println("Hàm getAllAuctions lấy được tổng số phiên: " + auctions.size());
    }

    // test getAllAuction()
    @Test
    void testGetAllAuctionMap_Success() throws SQLException {
        Map<Integer, Auction> auctionMap = auctionService.getAllAuction();

        assertNotNull(auctionMap, "Lỗi: Bản đồ đấu giá trả về bị null!");

        if (!auctionMap.isEmpty()) {
            Integer firstKey = auctionMap.keySet().iterator().next();
            assertNotNull(firstKey, "Lỗi: Key của Map bị null!");
            assertEquals(firstKey, auctionMap.get(firstKey).getAuctionId(),
                    "Lỗi: Cấu trúc Map sai lệch, Key không trùng khớp với AuctionId nằm bên trong đối tượng!");
        }
    }

    // test getAuction()
    @Test
    void testGetAuction_ValidId_Success() throws DatabaseException {
        Auction auction = auctionService.getAuction(dynamicAuctionId);

        assertNotNull(auction, "Lỗi: Tìm phiên đấu giá có thật trong DB nhưng kết quả lại trả về null!");
        assertEquals(dynamicAuctionId, auction.getAuctionId(), "Lỗi: Lấy ra sai ID của phiên đấu giá cần tìm!");
        System.out.println("Tìm thấy phiên đấu giá thành công. Tên món hàng: " + auction.getItemName());
    }

    @Test
    void testGetAuction_InvalidId_ReturnsNull() throws DatabaseException {
        int fakeAuctionId = -999999;

        Auction auction = auctionService.getAuction(fakeAuctionId);

        assertNull(auction, "Lỗi: Truyền ID không tồn tại nhưng hàm không trả về null theo đặc tả nghiệp vụ!");
    }


    //test getAuctionBySellerId(GetAuctionRequestDTO)
    @Test
    void testGetAuctionBySellerId_Success() throws DatabaseException {
        GetAuctionRequestDTO requestDTO = new GetAuctionRequestDTO();
        requestDTO.setSellerId(dynamicSellerId);

        GetAuctionResponseDTO responseDTO = auctionService.getAuctionBySellerId(requestDTO);

        assertNotNull(responseDTO, "Lỗi: Đối tượng Response DTO trả về bị null!");
        assertNotNull(responseDTO.getAuctionList(), "Lỗi: Danh sách List<Auction> bên trong Response DTO bị null!");

        System.out.println("-> Lọc danh sách theo Seller ID " + dynamicSellerId
                + " thành công. Số lượng tìm thấy: " + responseDTO.getAuctionList().size());
    }
}