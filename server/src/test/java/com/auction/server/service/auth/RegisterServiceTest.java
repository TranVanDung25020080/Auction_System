package com.auction.server.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import com.auction.common.enums.UserRole;
import com.auction.server.dao.UserDAO;
import com.auction.server.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterServiceTest {
    private UserDAO userDAO;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        authService = new AuthService(userDAO);
    }

    @Test
    //test đăng kí bidder thành công
    void testRegisterBidderSuccess() {
        String uniqueUser = "bidder_" + System.currentTimeMillis();

        boolean result = false;
        try {
            result = userDAO.registerUser("Test Bidder", uniqueUser, "pass123", UserRole.BIDDER);
        } catch (Exception e) {
            fail("Lỗi khi đăng ký Bidder: " + e.getMessage());
        }

        assertTrue(result, "Đăng ký Bidder phải thành công");
    }

    @Test
    //test đăng kí seller thành công
    void testRegisterSellerSuccess() {
        String uniqueUser = "seller_" + System.currentTimeMillis();

        boolean result = false;
        try {
            result = userDAO.registerUser("Test Seller", uniqueUser, "pass123", UserRole.SELLER);
        } catch (Exception e) {
            fail("Lỗi khi đăng ký Seller: " + e.getMessage());
        }

        assertTrue(result, "Đăng ký Seller phải thành công");
    }

    @Test
    //test đăng kí admin thành công
    void testRegisterAdminSuccess() {
        String uniqueUser = "admin_" + System.currentTimeMillis();

        boolean result = false;
        try {
            result = userDAO.registerUser("Test Admin", uniqueUser, "pass123", UserRole.ADMIN);
        } catch (Exception e) {
            fail("Lỗi khi đăng ký Admin: " + e.getMessage());
        }

        assertTrue(result, "Đăng ký Admin phải thành công");
    }

    @Test
    //Test báo lỗi khi đăng kí trùng tên admin
    void testRegisterDuplicateUsername() {
        assertThrows(com.auction.server.exception.DatabaseException.class, () -> {
            userDAO.registerUser("Duplicate", "admin", "123", UserRole.BIDDER);
        }, "Hệ thống phải quăng DatabaseException khi trùng username!");
    }
}