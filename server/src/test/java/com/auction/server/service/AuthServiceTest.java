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

    @Test
    void testLoginSuccess() {
        var response = authService.login("admin", "123");

        assertNotNull(response, "Response không được null");
        assertEquals("Login successfully", response.getMessage(), "Lỗi: " + response.getMessage());
    }

    @Test
    void testLoginWithWrongPassword() {
        var response = authService.login("admin", "sai_mat_khau_123456789");

        assertEquals("Invalid password", response.getMessage());
    }

    @Test
    void testLoginUserNotFound() {
        var response = authService.login("user_khong_ton_tai_999", "123");

        assertEquals("User not found", response.getMessage());
    }
}