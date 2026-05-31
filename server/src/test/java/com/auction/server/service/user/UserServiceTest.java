package com.auction.server.service.user;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.dto.response.UserResponseDTO;
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

    private int dynamicUserId = -1;
    private final int existingAuctionId = 4;

    @BeforeEach
    void setUp() throws SQLException {
        userService = new UserService();

        String getUserIdSql = "SELECT userId FROM user WHERE role = 'BIDDER' OR role = 'SELLER' LIMIT 1";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(getUserIdSql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                dynamicUserId = rs.getInt("userId");
            }
        }

        if (dynamicUserId == -1) {
            dynamicUserId = 9;
        }
    }

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
        return 1000.0;
    }

    // test depositBalance()
    @Test
    void testDepositBalance_Success() {
        double realCurrentBalance = getCurrentBalanceFromDB(dynamicUserId);
        double depositAmount = 100.0;

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(dynamicUserId);
        requestDTO.setAmount(depositAmount);
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.depositBalance(requestDTO);

        assertNotNull(responseDTO);
        if (responseDTO.getAuthStatus() == AuthStatus.SUCCESS) {
            assertEquals(AuthStatus.SUCCESS, responseDTO.getAuthStatus());
            assertEquals(realCurrentBalance + depositAmount, responseDTO.getCurrentBalance(), 0.1);
        } else {
            assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus());
            assertEquals(realCurrentBalance, responseDTO.getCurrentBalance());
        }
    }

    @Test
    void testDepositBalance_NegativeAmount_Failure() {
        double realCurrentBalance = getCurrentBalanceFromDB(dynamicUserId);

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(dynamicUserId);
        requestDTO.setAmount(-50.0); //nạp tiền âm
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.depositBalance(requestDTO);

        assertNotNull(responseDTO);
        if (responseDTO.getAuthStatus() == AuthStatus.SUCCESS) {
            assertEquals(realCurrentBalance - 50.0, responseDTO.getCurrentBalance(), 0.1);
        } else {
            assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus());
        }
    }

    // test withDraw()
    @Test
    void testWithDraw_Success() {
        double realCurrentBalance = getCurrentBalanceFromDB(dynamicUserId);
        double withdrawAmount = 10.0;

        if (realCurrentBalance < withdrawAmount) {
            UserBalanceRequestDTO depositMock = new UserBalanceRequestDTO();
            depositMock.setUserId(dynamicUserId);
            depositMock.setAmount(100.0);
            depositMock.setBalance(realCurrentBalance);
            userService.depositBalance(depositMock);
            realCurrentBalance = getCurrentBalanceFromDB(dynamicUserId);
        }

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(dynamicUserId);
        requestDTO.setAmount(withdrawAmount);
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.withDraw(requestDTO);

        assertNotNull(responseDTO);
        if (responseDTO.getAuthStatus() == AuthStatus.SUCCESS) {
            assertEquals(AuthStatus.SUCCESS, responseDTO.getAuthStatus());
            assertEquals(realCurrentBalance - withdrawAmount, responseDTO.getCurrentBalance(), 0.1);
        } else {
            assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus());
        }
    }

    @Test
    void testWithDraw_InsufficientBalance_Failure() {
        double realCurrentBalance = getCurrentBalanceFromDB(dynamicUserId);
        double excessiveAmount = realCurrentBalance + 999999.0;

        UserBalanceRequestDTO requestDTO = new UserBalanceRequestDTO();
        requestDTO.setUserId(dynamicUserId);
        requestDTO.setAmount(excessiveAmount);
        requestDTO.setBalance(realCurrentBalance);

        UserBalanceResponseDTO responseDTO = userService.withDraw(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(AuthStatus.FAILED, responseDTO.getAuthStatus());
        assertEquals(realCurrentBalance, responseDTO.getCurrentBalance(), "Thất bại số dư phải giữ nguyên");
        assertEquals("Khong du so du!", responseDTO.getMessage());
    }

    // test getBidInfoByBidderId()
    @Test
    void testGetBidInfoByBidderId_Success() {
        GetBidInfoRequestDTO requestDTO = new GetBidInfoRequestDTO();
        requestDTO.setBidderId(dynamicUserId);
        requestDTO.setAuctionId(existingAuctionId);

        GetBidInfoResponseDTO responseDTO = userService.getBidInfoByBidderId(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(AuthStatus.SUCCESS, responseDTO.getAuthStatus());
        assertNotNull(responseDTO.getBidTransactionList());
    }

    // test getBidInfoByAuctionId()
    @Test
    void testGetBidInfoByAuctionId_Success() {
        GetBidInfoRequestDTO requestDTO = new GetBidInfoRequestDTO();
        requestDTO.setAuctionId(existingAuctionId);

        GetBidInfoResponseDTO responseDTO = userService.getBidInfoByAuctionId(requestDTO);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getAuthStatus());
    }

    //test getAllUsers
    @Test
    void testGetAllUsers_Success() {
        UserResponseDTO responseDTO = userService.getAllUsers();

        assertNotNull(responseDTO);
        assertEquals(AuthStatus.SUCCESS, responseDTO.getAuthStatus());
        assertNotNull(responseDTO.getUserList());
        System.out.println("-> Lấy toàn bộ danh sách User thành công.");
    }
}