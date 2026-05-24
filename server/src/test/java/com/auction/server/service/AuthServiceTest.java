package com.auction.server.service;

import static org.junit.jupiter.api.Assertions.*;
import com.auction.server.dao.UserDAO;
import com.auction.server.service.auth.AuthService;
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
    //Test login thành công
    @Test
    void testLoginSuccess() {
        var response = authService.login("admin", "123");

        assertNotNull(response, "Response không được null");
        assertEquals("Login successfully", response.getMessage(), "Lỗi: " + response.getMessage());
    }
    //Test đăng nhập sai mật khẩu
    @Test
    void testLoginWithWrongPassword() {
        var response = authService.login("admin", "88888888");

        assertEquals("Invalid password", response.getMessage());
    }
    //Test đăng nhập với user không tồn tại
    @Test
    void testLoginUserNotFound() {
        var response = authService.login("user123", "123");

        assertEquals("User not found", response.getMessage());
    }

    //Test trường hợp để trống Username hoặc Password
    @Test
    void testLoginWithEmptyInput() {
        var response = authService.login("", "");
        assertEquals("User not found", response.getMessage());
    }

    @Test
    void testLoginWithNullInput() {
        var response = authService.login(null, null);
        assertEquals("User not found", response.getMessage());
    }

    //Test SQL Injection
    @Test
    void testLoginSqlInjection() {
        String sqlInjectionInput = "' OR '1'='1";
        var response = authService.login(sqlInjectionInput, "any_password");
        assertEquals("User not found", response.getMessage());
    }
}