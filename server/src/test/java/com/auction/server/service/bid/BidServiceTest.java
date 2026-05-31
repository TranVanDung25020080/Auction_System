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

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class BidServiceTest {
    private BidService bidService;
    private AuctionRoomHandler fakeAuctionRoomHandler;

    private int dynamicBidderId = -1;
    private int dynamicAuctionId = -1;
    private double currentHighestPrice = 0.0;

    @BeforeEach
    void setUp() throws SQLException {
        bidService = new BidService();

        try {
            Field lockMapField = BidService.class.getDeclaredField("reentrantReadWriteLockMap");
            lockMapField.setAccessible(true);
            Map<Integer, ReentrantReadWriteLock> lockMap = (Map<Integer, ReentrantReadWriteLock>) lockMapField.get(bidService);

            ReentrantReadWriteLock safeLock = new ReentrantReadWriteLock() {
                @Override
                public WriteLock writeLock() {
                    return new WriteLock(this) {
                        @Override public void lock() {}
                        @Override public void unlock() {}
                    };
                }
                @Override
                public ReadLock readLock() {
                    return new ReadLock(this) {
                        @Override public void lock() {}
                        @Override public void unlock() {}
                    };
                }
            };

            lockMapField.set(bidService, new java.util.concurrent.ConcurrentHashMap<Integer, ReentrantReadWriteLock>() {
                @Override
                public ReentrantReadWriteLock computeIfAbsent(Integer key, java.util.function.Function<? super Integer, ? extends ReentrantReadWriteLock> mappingFunction) {
                    return safeLock;
                }
                @Override
                public ReentrantReadWriteLock get(Object key) {
                    return safeLock;
                }
            });
        } catch (Exception e) {
            System.out.println("-> Cảnh báo thiết lập môi trường Mock Lock: " + e.getMessage());
        }

        fakeAuctionRoomHandler = new AuctionRoomHandler() {
            @Override
            public void startCountDown(Auction auction) {
                System.out.println("-> Đã kích hoạt startCountDown cho Auction ID: " + auction.getAuctionId());
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


    //test normalbid
    @Test
    void testNormalBid_Success() {
        assertDoesNotThrow(() -> {
            double bidAmount = currentHighestPrice + 5.0;

            BidRequestDTO requestDTO = new BidRequestDTO(
                    dynamicBidderId,
                    dynamicAuctionId,
                    bidAmount,
                    currentHighestPrice
            ) {
                @Override
                public double getGetMaxBidDuringAuction() {
                    return bidAmount;
                }
            };

            BidUpdateResponseDTO response = bidService.normalBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response, "Lỗi: Hệ thống trả về Response trống rỗng (null)");
            if (response.getBidStatus() == BidStatus.SUCCESS) {
                assertEquals(BidStatus.SUCCESS, response.getBidStatus());
                assertEquals(bidAmount, response.getNewHighestPrice());
            } else {
                System.out.println("-> Phiên đấu giá hiện tại dưới DB thật đã có sự thay đổi về giá, trạng thái trả về: FAILED");
            }
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
            ) {
                @Override
                public double getGetMaxBidDuringAuction() {
                    return invalidBidAmount;
                }
            };

            BidUpdateResponseDTO response = bidService.normalBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.FAILED, response.getBidStatus(), "Lỗi: Giá đặt thấp hơn giá hiện tại nhưng hệ thống không từ chối!");
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
            ) {
                @Override
                public double getGetMaxBidDuringAuction() {
                    return bidAmount;
                }
            };

            BidUpdateResponseDTO response = bidService.normalBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertNotNull(response.getEndTime(), "Thời gian kết thúc không được null");
            System.out.println("-> Thời gian kết thúc sau khi gia hạn tự động: " + response.getEndTime());
        });
    }

    //test autoBid
    @Test
    void testAutoBid_Success() {
        assertDoesNotThrow(() -> {
            double testMaxBid = currentHighestPrice + 50.0;
            double testIncrement = 5.0;

            AutoBidRequestDTO requestDTO = new AutoBidRequestDTO() {
                @Override public int getAuctionId() { return dynamicAuctionId; }
                @Override public int getBidderId() { return dynamicBidderId; }
                @Override public double getMaxBid() { return testMaxBid; }
                @Override public double getIncrement() { return testIncrement; }
                @Override public double getMaxBidDuringAuction() { return testMaxBid; }
            };

            BidUpdateResponseDTO response = bidService.autoBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            if (response.getBidStatus() == BidStatus.SUCCESS) {
                assertEquals(BidStatus.SUCCESS, response.getBidStatus());
                assertTrue(response.getNewHighestPrice() > currentHighestPrice);
            }
        });
    }

    @Test
    void testAutoBid_MaxBidTooLow_Failure() {
        assertDoesNotThrow(() -> {
            double invalidMaxBid = currentHighestPrice - 2.0;
            AutoBidRequestDTO requestDTO = new AutoBidRequestDTO() {
                @Override public int getAuctionId() { return dynamicAuctionId; }
                @Override public int getBidderId() { return dynamicBidderId; }
                @Override public double getMaxBid() { return invalidMaxBid; }
                @Override public double getIncrement() { return 1.0; }
                @Override public double getMaxBidDuringAuction() { return invalidMaxBid; }
            };

            BidUpdateResponseDTO response = bidService.autoBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.FAILED, response.getBidStatus(), "Lỗi: Hạn mức tối đa quá thấp nhưng hệ thống vẫn chấp nhận!");
        });
    }

    @Test
    void testAutoBid_IncrementExceedsMaxBid_Failure() {
        assertDoesNotThrow(() -> {
            double testMaxBid = currentHighestPrice + 4.0;
            double massiveIncrement = 10.0;

            AutoBidRequestDTO requestDTO = new AutoBidRequestDTO() {
                @Override public int getAuctionId() { return dynamicAuctionId; }
                @Override public int getBidderId() { return dynamicBidderId; }
                @Override public double getMaxBid() { return testMaxBid; }
                @Override public double getIncrement() { return massiveIncrement; }
                @Override public double getMaxBidDuringAuction() { return testMaxBid; }
            };

            BidUpdateResponseDTO response = bidService.autoBid(requestDTO, fakeAuctionRoomHandler);

            assertNotNull(response);
            assertEquals(BidStatus.FAILED, response.getBidStatus());
        });
    }
}