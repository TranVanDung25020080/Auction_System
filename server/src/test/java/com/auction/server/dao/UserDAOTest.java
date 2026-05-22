package com.auction.server.dao;

import com.auction.common.model.User.User;
import com.auction.server.exception.DatabaseException;
import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;
    private Connection testConnection;

    @BeforeEach
    void setUp() throws SQLException {
        userDAO = new UserDAO();
        testConnection = MyDatabaseConfig.getConnection();
        testConnection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (testConnection != null && !testConnection.isClosed()) {
            testConnection.rollback();
            testConnection.close();
        }
    }

    //Test tìm kiếm người dùng bằng ID thành công
    @Test
    void testGetUserById_Success() {
        int targetId = 3;
        try {
            User user = userDAO.getUserById(targetId);

            assertNotNull(user, "Lỗi: Không tìm thấy User dù ID tồn tại trong DB");
            assertEquals(targetId, user.getUserId());
            assertEquals("admin", user.getUserName());
        } catch (DatabaseException e) {
            fail("Không được ném ra lỗi ngoại lệ ở case này: " + e.getMessage());
        }
    }

    //Test tìm kiếm bằng ID không tồn tại trong hệ thống
    @Test
    void testGetUserById_NotFound() {
        int fakeId = 999999;
        try {
            User user = userDAO.getUserById(fakeId);
            assertNull(user, "Lỗi: Hàm phải trả về null khi không tìm thấy User");
        } catch (DatabaseException e) {
            fail("Tìm không thấy chỉ cần trả về null, không cần ném lỗi: " + e.getMessage());
        }
    }

    //Test lấy thông tin bằng Username hợp lệ
    @Test
    void testGetUserByUsername_Success() {
        String targetUsername = "admin";
        try {
            User user = userDAO.getUserByUsername(targetUsername);

            assertNotNull(user);
            assertEquals(3, user.getUserId());
            assertEquals("ADMIN", user.getUserRole().name());
        } catch (DatabaseException e) {
            fail("Lỗi truy vấn username: " + e.getMessage());
        }
    }

    //Test cập nhật số dư tài khoản thành công
    @Test
    void testUpdateBalance_Success() {
        int bidderId = 1;
        double addAmount = 100.0;

        try {
            User userBefore = userDAO.getUserById(bidderId);
            assertNotNull(userBefore);
            double originalBalance = userBefore.getBalance(); //Lấy số dư gốc của User

            userDAO.updateBalance(bidderId, addAmount);

            User userAfter = userDAO.getUserById(bidderId);
            assertEquals(originalBalance + addAmount, userAfter.getBalance(), //Cộng dồn với tiền thêm
                    "Lỗi: Số dư sau khi cập nhật không chính xác!");

            userDAO.updateBalance(bidderId, -addAmount);

        } catch (Exception e) {
            fail("Lỗi khi thực hiện update số dư: " + e.getMessage());
        }
    }

    //Test hàm lấy số dư showBalance hoạt động chính xác
    @Test
    void testShowBalance_Success() {
        int bidderId = 1;
        double addAmount = 100.0;

        try {
            double initialBalance = userDAO.showBalance(bidderId);

            userDAO.updateBalance(bidderId, addAmount);

            double balanceAfterUpdate = userDAO.showBalance(bidderId);

            double expectedBalance = initialBalance + addAmount;

            assertEquals(expectedBalance, balanceAfterUpdate,
                    "Lỗi: Hàm showBalance lấy sai số tiền sau khi được cộng dồn!");
            userDAO.updateBalance(bidderId, -addAmount);
        } catch (Exception e) {
            fail("Lỗi hệ thống khi gọi showBalance hoặc updateBalance: " + e.getMessage());
        }
    }
}