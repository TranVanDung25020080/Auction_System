package com.auction.server.service.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.server.auth.AuthService;
import com.auction.server.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {
    private UserDAO userDAO;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        authService = new AuthService(userDAO);
    }

    // Test đăng nhập sai mật khẩu
    @Test
    void testLoginWithWrongPassword() {
        var response = authService.login("admin", "88888888");
        assertEquals("Invalid password", response.getMessage());
    }

    // Test đăng nhập với user không tồn tại
    @Test
    void testLoginUserNotFound() {
        var response = authService.login("user123", "123");
        assertEquals("User not found", response.getMessage());
    }

    // Test trường hợp để trống Username hoặc Password
    @Test
    void testLoginWithEmptyInput() {
        var response = authService.login("", "");
        assertEquals("User not found", response.getMessage());
    }

    // Test SQL Injection
    @Test
    void testLoginSqlInjection() {
        String sqlInjectionInput = "' OR '1'='1";
        var response = authService.login(sqlInjectionInput, "any_password");
        assertEquals("User not found", response.getMessage());
    }

    // Test kiểm tra tính toàn vẹn dữ liệu trả về DTO
    @Test
    void testLoginReturnsCorrectUserData() {
        UserResponseDTO response = authService.login("admin", "123");

        assertNotNull(response, "Response không được null");
        assertEquals("Login successfully", response.getMessage());

        assertEquals("admin", response.getUserName(), "Username không khớp");
        assertNotNull(response.getUserRole(), "Role không được null");

        assertEquals(UserRole.ADMIN, response.getUserRole());
    }

    // Test trường hợp Username có khoảng trắng đầu/cuối
    @Test
    void testLoginWithSpacesInUsername() {
        var response = authService.login("  admin  ", "123");
        assertEquals("Login successfully", response.getMessage(),
                "Lỗi: Hệ thống chưa xử lý trim() khoảng trắng cho username");
    }

    // Test đăng nhập thành công với vai trò Bidder
    @Test
    void testLoginSuccessAsBidder() {
        UserResponseDTO response = authService.login("Htam", "helo");
        assertNotNull(response, "Response không được null");

        assertEquals("Login successfully", response.getMessage());
        assertEquals("Htam", response.getUserName(), "Username không khớp");
        assertEquals(UserRole.BIDDER, response.getUserRole(), "Sai phân quyền hệ thống");
    }

    // Test đăng nhập thành công với vai trò Seller
    @Test
    void testLoginSuccessAsSeller() {
        UserResponseDTO response = authService.login("DaddyDung", "dung123");
        assertNotNull(response, "Response không được null");
        assertEquals("Login successfully", response.getMessage());

        assertEquals("DaddyDung", response.getUserName(), "Username không khớp");
        assertEquals(UserRole.SELLER, response.getUserRole(), "Sai phân quyền hệ thống");
    }

    // Test đăng nhập thành công với vai trò Admin
    @Test
    void testLoginSuccessAsAdmin() {
        var response = authService.login("admin", "123");

        assertNotNull(response, "Response không được null");
        assertEquals("Login successfully", response.getMessage());

        assertEquals("admin", response.getUserName(), "Username không khớp");
        assertEquals(UserRole.ADMIN, response.getUserRole(), "Sai phân quyền hệ thống");
    }
}