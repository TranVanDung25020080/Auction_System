package com.auction.server.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import com.auction.common.enums.UserRole;
import com.auction.server.dao.UserDAO;
import com.auction.server.db.MyDatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;

class RegisterServiceTest {
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
        // Test đăng kí Bidder thành công
    void testRegisterBidderSuccess() {
        String uniqueUser = "bidder_" + System.currentTimeMillis();
        try {
            boolean result = userDAO.registerUser("Htam testBidder", uniqueUser, "123", UserRole.BIDDER);
            assertTrue(result, "Đăng ký Bidder thành công");
        } catch (Exception e) {
            fail("Lỗi khi đăng ký Bidder: " + e.getMessage());
        } finally {
            cleanUpUser(uniqueUser);
        }
    }

    @Test
        // Test đăng kí Seller thành công
    void testRegisterSellerSuccess() {
        String uniqueUser = "seller_" + System.currentTimeMillis();
        try {
            boolean result = userDAO.registerUser("Htam testSeller", uniqueUser, "123", UserRole.SELLER);
            assertTrue(result, "Đăng ký Seller thành công");
        } catch (Exception e) {
            fail("Lỗi khi đăng ký Seller: " + e.getMessage());
        } finally {
            cleanUpUser(uniqueUser);
        }
    }

    @Test
        // Test đăng kí Admin thành công
    void testRegisterAdminSuccess() {
        String uniqueUser = "admin_" + System.currentTimeMillis();
        try {
            boolean result = userDAO.registerUser("Htam testAdmin", uniqueUser, "123", UserRole.ADMIN);
            assertTrue(result, "Đăng ký Admin thành công");
        } catch (Exception e) {
            fail("Lỗi khi đăng ký Admin: " + e.getMessage());
        } finally {
            cleanUpUser(uniqueUser);
        }
    }

    @Test
        // Test báo lỗi khi đăng kí trùng tên thuộc tính userName
    void testRegisterDuplicateUsername() {
        String duplicateUser = "dup_" + System.currentTimeMillis();
        try {
            userDAO.registerUser("Test1", duplicateUser, "123", UserRole.BIDDER);

            assertThrows(Exception.class, () -> {
                userDAO.registerUser("Test2", duplicateUser, "456", UserRole.BIDDER);
            }, "Hệ thống phải quăng ngoại lệ bảo vệ khi trùng thuộc tính userName!");

        } catch (Exception e) {
            fail("Hệ thống gặp lỗi ngoài ý muốn trong quá trình chuẩn bị test: " + e.getMessage());
        } finally {
            cleanUpUser(duplicateUser);
        }
    }

    /**
     * Hàm dọn dẹp dựa trên chính xác tên bảng 'user' và cột 'userName' của em
     */
    private void cleanUpUser(String username) {
        String sql = "DELETE FROM user WHERE userName = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("[Warning] Không thể tự dọn rác user: " + username + " - " + e.getMessage());
        }
    }
}