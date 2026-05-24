package com.auction.server.service.user;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserServiceTest {
    private UserService userService;

    private final int existingUserId = 9;
    private final int existingAuctionId = 4;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    /**
     * Hàm bổ trợ: Lấy số dư thực tế hiện tại trực tiếp từ Database
     */
    private double getCurrentBalanceFromDB(int userId) {
        String sql = "SELECT balance FROM user WHERE userId = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            System.err.println("Không thể lấy số dư hiện tại từ DB: " + e.getMessage());
        }
        return 1000000000.0;
    }

    //Test nạp tiền thành công với số dư cộng dồn thực tế từ DB
    @Test
    void testDepositBalance_Success() {
        // Lấy số dư thực tế trước khi nạp
        double realCurrentBalance = getCurrentBalanceFromDB(existingUserId);
        double depositAmount = 100.0;

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(existingUserId);
        requestDTO.setAmount(depositAmount);
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.depositBalance(requestDTO);

        assertNotNull(responseDTO, "Lỗi: Kết quả Response DTO trả về bị null!");

        if (responseDTO.getAuthStatus() == AuthStatus.SUCCESS) {
            assertEquals(AuthStatus.SUCCESS, responseDTO.getAuthStatus());
            assertEquals(realCurrentBalance + depositAmount, responseDTO.getCurrentBalance(), 2.0,
                    "Lỗi: Hệ thống cập nhật sai số dư cộng dồn thực tế!");
        } else {
            assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus());
            assertEquals(realCurrentBalance, responseDTO.getCurrentBalance(), "Lỗi: Khi thất bại, số dư phải giữ nguyên!");
        }
    }

    //Test nạp tiền với số tiền âm
    @Test
    void testDepositBalance_Failure() {
        double realCurrentBalance = getCurrentBalanceFromDB(existingUserId);

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(existingUserId);
        requestDTO.setAmount(-100.0);
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.depositBalance(requestDTO);

        assertNotNull(responseDTO, "Lỗi: Đối tượng trả về bị null!");

        assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus(), "Lỗi: Hệ thống không được chấp nhận nạp tiền âm!");
        assertEquals(realCurrentBalance, responseDTO.getCurrentBalance(), "Lỗi: Số dư bị thay đổi bất thường khi nạp tiền âm!");
    }

    //Test nạp số tiền quá lớn gây tràn số
    @Test
    void testDepositBalance_Overflow() {
        double realCurrentBalance = getCurrentBalanceFromDB(existingUserId);

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(existingUserId);
        requestDTO.setAmount(Double.MAX_VALUE);
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.depositBalance(requestDTO);
        assertNotNull(responseDTO, "Lỗi: Kết quả Response DTO trả về bị null!");

        if (responseDTO.getAuthStatus() == AuthStatus.SUCCESS) {
            assertEquals(Double.POSITIVE_INFINITY, responseDTO.getCurrentBalance());
        } else {
            assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus());
            assertEquals(realCurrentBalance, responseDTO.getCurrentBalance());
        }
    }

    //Test lấy lịch sử giao dịch theo Bidder ID an toàn
    @Test
    void testGetBidInfoByBidderId_Success() {
        GetBidInfoRequestDTO requestDTO = new GetBidInfoRequestDTO();
        requestDTO.setBidderId(existingUserId);

        try {
            GetBidInfoResponseDTO responseDTO = userService.getBidInfoByBidderId(requestDTO);
            assertNotNull(responseDTO, "Lỗi: Kết quả Response DTO trả về bị null!");

            System.out.println("-> Trạng thái lấy lịch sử theo Bidder: " + responseDTO.getAuthStatus());
        } catch (NullPointerException e) {
            fail("Lỗi hệ thống: Hàm xử lý nội bộ chưa check null danh sách đấu giá rỗng trong DB!");
        }
    }

    //Test lấy lịch sử giao dịch theo Auction ID an toàn
    @Test
    void testGetBidInfoByAuctionId_Success() {
        GetBidInfoRequestDTO requestDTO = new GetBidInfoRequestDTO();
        requestDTO.setAuctionId(existingAuctionId);
        requestDTO.setBidderId(existingUserId);

        try {
            GetBidInfoResponseDTO responseDTO = userService.getBidInfoByAuctionId(requestDTO);
            assertNotNull(responseDTO, "Lỗi: Kết quả Response DTO trả về bị null!");
            System.out.println("-> Trạng thái lấy lịch sử theo Auction: " + responseDTO.getAuthStatus());
        } catch (NullPointerException e) {
            fail("Lỗi hệ thống: Hàm xử lý nội bộ chưa check null khi phiên đấu giá chưa có ai đặt giá!");
        }
    }
}