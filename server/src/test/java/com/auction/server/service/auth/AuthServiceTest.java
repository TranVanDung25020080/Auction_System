package com.auction.server.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.server.exception.AuthException;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class AuthServiceTest {
    private LoginService loginService;
    private SignUpService signUpService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
        signUpService = new SignUpService();
    }

    @Test
        //Test đăng nhập sai mật khẩu
    void testLoginWithWrongPassword() {
        String uniqueUser = "login_fail_" + System.currentTimeMillis();
        try {
            signUpService.signUp("Tester", uniqueUser, "correct_pass", UserRole.BIDDER);

            AuthException exception = assertThrows(AuthException.class, () -> {
                loginService.login(uniqueUser, "wrong_pass_999");
            }, "Lỗi: Đăng nhập sai mật khẩu nhưng hệ thống không chặn!");

            assertEquals("wrong username or password!", exception.getMessage());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUpUser(uniqueUser);
        }
    }

    @Test
        //Test đăng nhập với user không tồn tại trong hệ thống
    void testLoginUserNotFound() {
        String nonExistUser = "ghost_user_" + System.currentTimeMillis();
        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login(nonExistUser, "123");
        });
        assertEquals("wrong username or password!", exception.getMessage());
    }

    @Test
        //Test trường hợp để trống dữ liệu đầu vào
    void testLoginWithEmptyInput() {
        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login("", "");
        });
        assertEquals("wrong username or password!", exception.getMessage());
    }

    @Test
        //Test chống tấn công SQL Injection thông qua ô đăng nhập
    void testLoginSqlInjection() {
        String sqlInjectionInput = "' OR '1'='1";
        AuthException exception = assertThrows(AuthException.class, () -> {
            loginService.login(sqlInjectionInput, "666666");
        });
        assertEquals("wrong username or password!", exception.getMessage());
    }

    @Test
        //Test đăng nhập thành công với vai trò ADMIN
    void testLoginSuccessAsAdmin() {
        String adminUser = "dynamic_admin_" + System.currentTimeMillis();
        try {
            signUpService.signUp("Htam Admin", adminUser, "admin123", UserRole.ADMIN);

            assertDoesNotThrow(() -> {
                UserResponseDTO response = loginService.login(adminUser, "admin123");
                assertNotNull(response);
                assertEquals(adminUser, response.getUserName());
                assertEquals(UserRole.ADMIN, response.getUserRole());
            });
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUpUser(adminUser);
        }
    }

    @Test
        //Test đăng nhập thành công với vai trò BIDDER
    void testLoginSuccessAsBidder() {
        String bidderUser = "dynamic_bidder_" + System.currentTimeMillis();
        try {
            signUpService.signUp("Htam Bidder", bidderUser, "bidder123", UserRole.BIDDER);

            assertDoesNotThrow(() -> {
                UserResponseDTO response = loginService.login(bidderUser, "bidder123");
                assertNotNull(response);
                assertEquals(bidderUser, response.getUserName());
                assertEquals(UserRole.BIDDER, response.getUserRole());
            });
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUpUser(bidderUser);
        }
    }

    @Test
        //Test đăng nhập thành công với vai trò SELLER
    void testLoginSuccessAsSeller() {
        String sellerUser = "dynamic_seller_" + System.currentTimeMillis();
        try {
            signUpService.signUp("Htam Seller", sellerUser, "seller123", UserRole.SELLER);

            assertDoesNotThrow(() -> {
                UserResponseDTO response = loginService.login(sellerUser, "seller123");
                assertNotNull(response);
                assertEquals(sellerUser, response.getUserName());
                assertEquals(UserRole.SELLER, response.getUserRole());
            });
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUpUser(sellerUser);
        }
    }

    @Test
        //Test luồng đăng ký tài khoản mới thành công
    void testSignUp_Success() {
        String uniqueUsername = "signup_ok_" + System.currentTimeMillis();
        try {
            assertDoesNotThrow(() -> {
                UserResponseDTO response = signUpService.signUp("Tester", uniqueUsername, "123", UserRole.BIDDER);
                assertNotNull(response);
                assertEquals(uniqueUsername, response.getUserName());
                assertEquals(UserRole.BIDDER, response.getUserRole());
            });
        } finally {
            cleanUpUser(uniqueUsername);
        }
    }

    @Test
        //kiểm thử đăng ký mới tài khoản mặc định số dư bằng 0.0
    void testSignUp_DefaultBalanceCheck() {
        String balanceCheckUser = "balance_" + System.currentTimeMillis();
        try {
            signUpService.signUp("Tai khoản test balance", balanceCheckUser, "123456", UserRole.BIDDER);

            double actualBalance = getUserBalanceFromDB(balanceCheckUser);
            assertEquals(0.0, actualBalance, "Lỗi: Tài khoản mới tạo nhưng thuộc tính balance không mặc định bằng 0.0!");

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUpUser(balanceCheckUser);
        }
    }

    @Test
        //Test chặn đăng ký trùng tên userName
    void testRegisterDuplicateUsername() {
        String duplicateUser = "dup_reg_" + System.currentTimeMillis();
        try {
            signUpService.signUp("User Thật", duplicateUser, "123", UserRole.BIDDER);

            assertThrows(Exception.class, () -> {
                signUpService.signUp("User Trùng", duplicateUser, "456", UserRole.BIDDER);
            }, "Lỗi: Hệ thống cho phép đăng ký trùng tài khoản!");

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUpUser(duplicateUser);
        }
    }

    /**
     * Hàm phụ trợ lấy số dư balance thực tế từ DB để thực hiện Assertions
     */
    private double getUserBalanceFromDB(String username) {
        String sql = "SELECT balance FROM user WHERE userName = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (Exception e) {
            System.out.println("[Error] Không lấy được balance của user: " + username);
        }
        return -1.0;
    }

    /**
     * Hàm phụ trợ dọn dẹp tài khoản test mồi trên bảng 'user'
     */
    private void cleanUpUser(String username) {
        String sql = "DELETE FROM user WHERE userName = ?";
        try (Connection conn = MyDatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("[Warning] Không thể xóa user mồi: " + username);
        }
    }
}