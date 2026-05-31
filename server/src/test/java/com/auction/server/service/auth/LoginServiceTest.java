package com.auction.server.service.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.server.dao.UserDAO;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.AuthException;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class LoginServiceTest {
    private LoginService loginService;
    private UserDAO userDAO;

    private final String testBidderUser = "bidder_login_" + System.currentTimeMillis();
    private final String testSellerUser = "seller_login_" + System.currentTimeMillis();
    private final String testAdminUser = "admin_login_" + System.currentTimeMillis();
    private final String testPassword = "123";

    @BeforeEach
    void setUp() throws DatabaseException, SQLException {
        loginService = new LoginService();
        userDAO = new UserDAO();

        userDAO.registerUser("Test Bidder", testBidderUser, testPassword, UserRole.BIDDER);
        userDAO.registerUser("Test Seller", testSellerUser, testPassword, UserRole.SELLER);
        userDAO.registerUser("Test Admin", testAdminUser, testPassword, UserRole.ADMIN);
    }

    @AfterEach
    void tearDown() throws SQLException {
        String deleteSql = "DELETE FROM user WHERE userName IN (?, ?, ?)";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteSql)) {
            pst.setString(1, testBidderUser);
            pst.setString(2, testSellerUser);
            pst.setString(3, testAdminUser);
            pst.executeUpdate();
        }
    }

    // Test đăng nhập thành công (login)
    @Test
    void testLoginSuccessAsBidder() {
        assertDoesNotThrow(() -> {
            UserResponseDTO response = loginService.login(testBidderUser, testPassword);

            assertNotNull(response, "Response không được null khi đăng nhập đúng");
            assertEquals(testBidderUser, response.getUserName());
            assertEquals(UserRole.BIDDER, response.getUserRole(), "Sai phân quyền hệ thống đối với Bidder");
        });
    }

    @Test
    void testLoginSuccessAsSeller() {
        assertDoesNotThrow(() -> {
            UserResponseDTO response = loginService.login(testSellerUser, testPassword);

            assertNotNull(response);
            assertEquals(testSellerUser, response.getUserName());
            assertEquals(UserRole.SELLER, response.getUserRole(), "Sai phân quyền hệ thống đối với Seller");
        });
    }

    @Test
    void testLoginSuccessAsAdmin() {
        assertDoesNotThrow(() -> {
            UserResponseDTO response = loginService.login(testAdminUser, testPassword);

            assertNotNull(response);
            assertEquals(testAdminUser, response.getUserName());
            assertEquals(UserRole.ADMIN, response.getUserRole(), "Sai phân quyền hệ thống đối với Admin");
        });
    }

    // test đăng nhập thất bại
    @Test
    void testLoginWithWrongPassword_ShouldThrowAuthException() {
        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login(testBidderUser, "264grh5uwgwg");
        }, "Hệ thống phải ném AuthException khi nhập sai mật khẩu!");

        assertEquals("wrong username or password!", exception.getMessage());
    }

    @Test
    void testLoginUserNotFound_ShouldThrowAuthException() {
        String nonExistingUser = "ghost_user_9999";

        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login(nonExistingUser, testPassword);
        }, "Hệ thống phải ném AuthException khi tài khoản không tồn tại!");

        assertEquals("wrong username or password!", exception.getMessage());
    }

    @Test
    void testLoginWithEmptyInput_ShouldThrowAuthException() {
        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login("", "");
        });
        assertEquals("wrong username or password!", exception.getMessage());
    }

    @Test
    void testLoginSqlInjection_ShouldThrowAuthException() {
        String sqlInjectionInput = "' OR '1'='1";

        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login(sqlInjectionInput, "154tw4gw2gq2fq");
        });
        assertEquals("wrong username or password!", exception.getMessage());
    }
}