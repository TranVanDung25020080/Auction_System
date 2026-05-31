package com.auction.server.dao;

import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;

    private final String testOwnerName = "TRAN DANG TEST";
    private final String testUserName = "trandang_test_2026";
    private final String testPassword = "password123";
    private final UserRole testRole = UserRole.BIDDER;

    private int dynamicUserId = -1;

    @BeforeEach
    void setUp() throws SQLException {
        userDAO = new UserDAO();

        String getUserIdSql = "SELECT userId FROM user WHERE (role = 'BIDDER' OR role = 'SELLER') AND balance IS NOT NULL LIMIT 1";
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

        cleanTestUser();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanTestUser();
    }
    private void cleanTestUser() throws SQLException {
        String deleteSql = "DELETE FROM user WHERE userName = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteSql)) {
            pst.setString(1, testUserName);
            pst.executeUpdate();
        }
    }

    //test phương thức registerUser()
    @Test
    void testRegisterUser_Success() {
        assertDoesNotThrow(() -> {
            boolean result = userDAO.registerUser(testOwnerName, testUserName, testPassword, testRole);
            assertTrue(result, "Lỗi: Hàm trả về false, đăng ký tài khoản thất bại!");

            String verifySql = "SELECT userId FROM user WHERE userName = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(verifySql)) {
                pst.setString(1, testUserName);
                try (ResultSet rs = pst.executeQuery()) {
                    assertTrue(rs.next(), "Lỗi: Hệ thống báo đăng ký thành công nhưng không tìm thấy data trong DB!");
                }
            }
        }, "Lỗi: Quá trình đăng ký người dùng ném ngoại lệ không mong muốn!");
    }


    //test login()
    @Test
    void testLogin_Success() {
        assertDoesNotThrow(() -> {
            userDAO.registerUser(testOwnerName, testUserName, testPassword, testRole);

            User user = userDAO.login(testUserName, testPassword);

            assertNotNull(user, "Lỗi: Đăng nhập thất bại, thực thể User trả về bị null!");
            assertEquals(testOwnerName, user.getOwnerName(), "Lỗi: Sai thông tin tên chủ sở hữu!");
            assertEquals(testRole, user.getUserRole(), "Lỗi: Sai quyền hạn của User!");
        });
    }

    @Test
    void testLogin_WrongPassword_ShouldReturnNull() {
        assertDoesNotThrow(() -> {
            userDAO.registerUser(testOwnerName, testUserName, testPassword, testRole);

            User user = userDAO.login(testUserName, "mat_khau_sai");

            assertNull(user, "Lỗi: Nhập sai mật khẩu hệ thống vẫn trả về thực thể User đăng nhập thành công!");
        });
    }


    // test showBalance()
    @Test
    void testShowBalance_Success() {
        assertDoesNotThrow(() -> {
            double balance = userDAO.showBalance(dynamicUserId);
            assertTrue(balance >= 0, "Lỗi: Số dư lấy từ Database bị âm bất thường!");
            System.out.println("Ví tiền của User " + dynamicUserId + " hiện tại: " + balance);
        });
    }

    @Test
    void testShowBalance_UserNotFound_ShouldThrowException() {
        int fakeUserId = -8888;

        DatabaseException exception = assertThrows(DatabaseException.class, () -> {
            userDAO.showBalance(fakeUserId);
        }, "Lỗi: Truy vấn ID không tồn tại nhưng hàm không quăng DatabaseException để xử lý!");

        assertTrue(exception.getMessage().contains("Khong tim thay id nguoi dung nay"),
                "Lỗi: Nội dung thông báo lỗi trong Exception không chính xác!");
    }

    // test updateBalance()
    @Test
    void testUpdateBalance_Success() {
        assertDoesNotThrow(() -> {
            double currentBalance = userDAO.showBalance(dynamicUserId);
            double newBalanceAmount = currentBalance + 1000.0;

            userDAO.updateBalance(dynamicUserId, newBalanceAmount);

            double balanceAfterUpdate = userDAO.showBalance(dynamicUserId);
            assertEquals(newBalanceAmount, balanceAfterUpdate, "Lỗi: Số dư trong DB chưa được cập nhật chính xác sang số tiền mới!");
        });
    }

    // test getAllUsers()
    @Test
    void testGetAllUsers_Success() {
        assertDoesNotThrow(() -> {
            List<User> list = userDAO.getAllUsers();
            assertNotNull(list, "Lỗi: Danh sách người dùng trả về bị null!");
            assertFalse(list.isEmpty(), "Lỗi: Hệ thống đang chạy thực tế có data nhưng hàm lại trả về danh sách rỗng!");
            System.out.println("Tìm thấy tổng số " + list.size() + " người dùng trong hệ thống.");
        });
    }
}