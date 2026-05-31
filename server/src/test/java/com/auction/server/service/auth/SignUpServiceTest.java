package com.auction.server.service.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.server.db.MyDatabaseConfig;
import com.auction.server.exception.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class SignUpServiceTest {
    private SignUpService signUpService;

    private String cleanableDuplicateUsername = null;

    @BeforeEach
    void setUp() {
        signUpService = new SignUpService();
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (cleanableDuplicateUsername != null) {
            String deleteSql = "DELETE FROM user WHERE userName = ?";
            try (Connection conn = MyDatabaseConfig.getConnection();
                 PreparedStatement pst = conn.prepareStatement(deleteSql)) {
                pst.setString(1, cleanableDuplicateUsername);
                pst.executeUpdate();
            }
        }
    }

    //Test đăng kí thành công
    @Test
    void testRegisterBidderSuccess() {
        String uniqueUser = "bidder_signup_" + System.currentTimeMillis();

        assertDoesNotThrow(() -> {
            UserResponseDTO responseDTO = signUpService.signUp("Test Bidder", uniqueUser, "123", UserRole.BIDDER);

            assertNotNull(responseDTO, "Kết quả trả về không được null khi đăng ký thành công!");
            assertEquals(uniqueUser, responseDTO.getUserName(), "Username trong DTO trả về không khớp!");
            assertEquals(UserRole.BIDDER, responseDTO.getUserRole(), "Quyền UserRole trả về bị sai lệch!");
        });
    }

    @Test
    void testRegisterSellerSuccess() {
        String uniqueUser = "seller_signup_" + System.currentTimeMillis();

        assertDoesNotThrow(() -> {
            UserResponseDTO responseDTO = signUpService.signUp("Test Seller", uniqueUser, "123", UserRole.SELLER);

            assertNotNull(responseDTO, "Kết quả trả về không được null khi đăng ký thành công!");
            assertEquals(uniqueUser, responseDTO.getUserName());
            assertEquals(UserRole.SELLER, responseDTO.getUserRole());
        });
    }

    @Test
    void testRegisterAdminSuccess() {
        String uniqueUser = "admin_signup_" + System.currentTimeMillis();

        assertDoesNotThrow(() -> {
            UserResponseDTO responseDTO = signUpService.signUp("Test Admin", uniqueUser, "123", UserRole.ADMIN);

            assertNotNull(responseDTO, "Kết quả trả về không được null khi đăng ký thành công!");
            assertEquals(uniqueUser, responseDTO.getUserName());
            assertEquals(UserRole.ADMIN, responseDTO.getUserRole());
        });
    }


    //Test đăng kí trùng tên tài khoản
    @Test
    void testRegisterDuplicateUsername_ShouldThrowDatabaseException() {
        String duplicateUser = "dup_signup_" + System.currentTimeMillis();
        cleanableDuplicateUsername = duplicateUser;

        assertDoesNotThrow(() -> {
            signUpService.signUp("huhuhuhhu", duplicateUser, "123", UserRole.BIDDER);
        });

        assertThrows(DatabaseException.class, () -> {
            signUpService.signUp("hehehehehe", duplicateUser, "456", UserRole.BIDDER);
        }, "Hệ thống phải ném ra DatabaseException khi đăng ký tài khoản bị trùng username!");
    }
}