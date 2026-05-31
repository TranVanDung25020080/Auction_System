package com.auction.server.service.bid;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.handler.socketserver.AuctionRoomHandler;
import com.auction.server.service.auction.BidService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

class BidServiceTest {
    private BidService bidService;
    private AuctionRoomHandler fakeAuctionRoomHandler;

    private int dynamicBidderId = -1;
    private int dynamicAuctionId = -1;
    private double currentHighestPrice = 0.0;

    @BeforeEach
    void setUp() throws SQLException {
        bidService = new BidService();

        fakeAuctionRoomHandler = new AuctionRoomHandler() {
            @Override
            public void startCountDown(Auction auction) {
                System.out.println("->Đã kích hoạt startCountDown cho Auction ID: " + auction.getAuctionId());
            }
        };

        String getBidderSql = "SELECT userId FROM user WHERE role = 'BIDDER' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getBidderSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicBidderId = rs.getInt("userId");
            }
        }

        String getAuctionSql = "SELECT auctionId, currentHighestPrice FROM auction WHERE status = 'OPEN' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getAuctionSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicAuctionId = rs.getInt("auctionId");
                currentHighestPrice = rs.getDouble("currentHighestPrice");
            }
        }

        if (dynamicBidderId == -1) dynamicBidderId = 8;
        if (dynamicAuctionId == -1) {
            dynamicAuctionId = 4;
            currentHighestPrice = 10.0;
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        String cleanBidsSql = "DELETE FROM bid_transaction WHERE bidderId = ? AND auctionId = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(cleanBidsSql)) {
            pst.setInt(1, dynamicBidderId);
            pst.setInt(2, dynamicAuctionId);
            pst.executeUpdate();
        }
    }

    //test normalBid()

    @Test
    void testNormalBid_Success() {
        assertDoesNotThrow(() -> {
            double bidAmount = currentHighestPrice + 5.0;

            BidRequestDTO requestDTO = new BidRequestDTO(
                    dynamicBidderId,
                    dynamicAuctionId,
                    bidAmount,
                    currentHighestPrice
            );

            BidUpdateResponseDTO response = bidService.normalBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.SUCCESS, response.getBidStatus());
            assertEquals(bidAmount, response.getNewHighestPrice());
        });
    }

    @Test
    void testNormalBid_LowPrice_Failure() {
        assertDoesNotThrow(() -> {
            double invalidBidAmount = currentHighestPrice - 2.0;
            BidRequestDTO requestDTO = new BidRequestDTO(
                    dynamicBidderId,
                    dynamicAuctionId,
                    invalidBidAmount,
                    currentHighestPrice
            );

            BidUpdateResponseDTO response = bidService.normalBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.FAILED, response.getBidStatus());
        });
    }

    @Test
    void testNormalBid_ExtendEndTime_Success() throws SQLException {
        LocalDateTime closeTime = LocalDateTime.now().plusSeconds(5);
        String updateTimeSql = "UPDATE auction SET endTime = ? WHERE auctionId = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(updateTimeSql)) {
            pst.setTimestamp(1, Timestamp.valueOf(closeTime));
            pst.setInt(2, dynamicAuctionId);
            pst.executeUpdate();
        }

        assertDoesNotThrow(() -> {
            double bidAmount = currentHighestPrice + 10.0;

            BidRequestDTO requestDTO = new BidRequestDTO(
                    dynamicBidderId,
                    dynamicAuctionId,
                    bidAmount,
                    currentHighestPrice
            );

            BidUpdateResponseDTO response = bidService.normalBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertTrue(response.getEndTime().isAfter(LocalDateTime.now().plusSeconds(15)),
                    "Lỗi: Thời gian kết thúc chưa được cộng thêm 20 giây!");
        });
    }

    // test autoBid()
    @Test
    void testAutoBid_Success() {
        assertDoesNotThrow(() -> {
            double testMaxBid = currentHighestPrice + 50.0;
            double testIncrement = 5.0;

            AutoBidRequestDTO requestDTO = new AutoBidRequestDTO() {
                @Override
                public int getAuctionId() { return dynamicAuctionId; }
                @Override
                public int getBidderId() { return dynamicBidderId; }
                @Override
                public double getMaxBid() { return testMaxBid; }
                @Override
                public double getIncrement() { return testIncrement; }
            };

            BidUpdateResponseDTO response = bidService.autoBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.SUCCESS, response.getBidStatus());
            assertEquals(currentHighestPrice + testIncrement, response.getNewHighestPrice());
        });
    }

    @Test
    void testAutoBid_MaxBidTooLow_Failure() {
        assertDoesNotThrow(() -> {
            double invalidMaxBid = currentHighestPrice - 2.0;
            AutoBidRequestDTO requestDTO = new AutoBidRequestDTO() {
                @Override
                public int getAuctionId() { return dynamicAuctionId; }
                @Override
                public int getBidderId() { return dynamicBidderId; }
                @Override
                public double getMaxBid() { return invalidMaxBid; }
                @Override
                public double getIncrement() { return 1.0; }
            };

            BidUpdateResponseDTO response = bidService.autoBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.FAILED, response.getBidStatus());
        });
    }

    @Test
    void testAutoBid_IncrementExceedsMaxBid_Failure() {
        assertDoesNotThrow(() -> {
            double testMaxBid = currentHighestPrice + 4.0;
            double massiveIncrement = 10.0;

            AutoBidRequestDTO requestDTO = new AutoBidRequestDTO() {
                @Override
                public int getAuctionId() { return dynamicAuctionId; }
                @Override
                public int getBidderId() { return dynamicBidderId; }
                @Override
                public double getMaxBid() { return testMaxBid; }
                @Override
                public double getIncrement() { return massiveIncrement; }
            };

            BidUpdateResponseDTO response = bidService.autoBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.FAILED, response.getBidStatus());
        });
    }
}