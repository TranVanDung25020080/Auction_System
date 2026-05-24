package com.auction.server.dao;

import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;
import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;
    private Connection testConnection;

    private final String testOwnerName = "huhuhihihehe";
    private final String testUsername = "HtamDepChai";
    private final String testPassword = "123123";

    @BeforeEach
    void setUp() throws SQLException {
        userDAO = new UserDAO();
        testConnection = MyDatabaseConfig.getConnection();
        testConnection.setAutoCommit(false);

        String cleanQuery = "DELETE FROM user WHERE username = ?";
        try (PreparedStatement pst = testConnection.prepareStatement(cleanQuery)) {
            pst.setString(1, testUsername);
            pst.executeUpdate();
            testConnection.commit();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        String cleanQuery = "DELETE FROM user WHERE username = ?";
        try (PreparedStatement pst = testConnection.prepareStatement(cleanQuery)) {
            pst.setString(1, testUsername);
            pst.executeUpdate();
            testConnection.commit();
        }

        if (testConnection != null && !testConnection.isClosed()) {
            testConnection.close();
        }
    }

    //Test đăng ký tài khoản thành công
    @Test
    void testRegisterUser_Success() {
        assertDoesNotThrow(() -> {
            boolean isRegistered = userDAO.registerUser(testOwnerName, testUsername, testPassword, UserRole.BIDDER);
            assertTrue(isRegistered, "Lỗi: Đăng ký thất bại dù thông tin hợp lệ!");
        });
    }

    //Test đăng nhập thành công
    @Test
    void testLogin_Success() {
        assertDoesNotThrow(() -> {
            userDAO.registerUser(testOwnerName, testUsername, testPassword, UserRole.BIDDER);
            User loggedUser = userDAO.login(testUsername, testPassword);

            assertNotNull(loggedUser, "Lỗi: Không đăng nhập được!");
            assertEquals(testUsername, loggedUser.getUserName(), "Lỗi: Sai tên tài khoản đăng nhập!");
        });
    }

    //Test đăng nhập thất bại do sai mật khẩu
    @Test
    void testLogin_WrongPassword() {
        assertDoesNotThrow(() -> {
            userDAO.registerUser(testOwnerName, testUsername, testPassword, UserRole.BIDDER);
            User loggedUser = userDAO.login(testUsername, "Mật khẩu không đúng!");

            assertNull(loggedUser, "Lỗi: Hệ thống không được phép cho đăng nhập khi sai mật khẩu!");
        });
    }

    //Test tìm kiếm người dùng qua username
    @Test
    void testGetUserByUsername_Success() {
        assertDoesNotThrow(() -> {
            userDAO.registerUser(testOwnerName, testUsername, testPassword, UserRole.BIDDER);
            User user = userDAO.getUserByUsername(testUsername);

            assertNotNull(user, "Lỗi: Không tìm thấy user bằng username!");
            assertEquals(testUsername, user.getUserName(), "Lỗi: Username bốc ra bị lệch!");
        });
    }

    //Test cập nhật số dư tài khoản & Xem số dư
    @Test
    void testUpdateAndShowBalance_Success() {
        assertDoesNotThrow(() -> {
            int targetUserId = 9;
            double balanceAmount = 500.0;
            double originalBalance = userDAO.showBalance(targetUserId);
            double newBalanceAmount = originalBalance + balanceAmount;

            userDAO.updateBalance(targetUserId, newBalanceAmount);

            double updatedBalance = userDAO.showBalance(targetUserId);
            assertEquals(newBalanceAmount, updatedBalance, "Lỗi: Số dư sau khi cập nhật không khớp!");

            userDAO.updateBalance(targetUserId, originalBalance);
        }, "Lỗi hệ thống khi xử lý hàm kiểm thử số dư!");
    }
}