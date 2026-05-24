package com.auction.common.dto;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDTOTest {
    @Test
    //Test cons 5 tham số của UserResponseDTO
    void testConstructorFiveParameters() {
        int expectedId = 1;
        String expectedOwner = "Daddy Tam";
        String expectedUserName = "Htam";
        double expectedBalance = 150.0;
        UserRole expectedRole = UserRole.BIDDER;

        UserResponseDTO userResponse = new UserResponseDTO(expectedId, expectedOwner, expectedUserName, expectedBalance, expectedRole);

        assertEquals(expectedId, userResponse.getUserId(), "Lỗi: Khởi tạo userId bị sai!");
        assertEquals(expectedOwner, userResponse.getOwnerName(), "Lỗi: Khởi tạo ownerName bị sai!");
        assertEquals(expectedUserName, userResponse.getUserName(), "Lỗi: Khởi tạo userName bị sai!");
        assertEquals(expectedBalance, userResponse.getBalance(), "Lỗi: Khởi tạo balance bị sai!");
        assertEquals(expectedRole, userResponse.getUserRole(), "Lỗi: Khởi tạo userRole bị sai!");
    }
    @Test
    //Test cons 3 tham số của UserResponseDTO
    void testConstructorThreeParameters() {
        String expectedOwnerName = "Nguyen Van A";
        String expectedUserName = "admin";
        UserRole expectedRole = UserRole.ADMIN;

        UserResponseDTO userResponse = new UserResponseDTO(expectedOwnerName, expectedUserName, expectedRole);

        assertEquals(expectedOwnerName, userResponse.getOwnerName());
        assertEquals(expectedUserName, userResponse.getUserName());
        assertEquals(expectedRole, userResponse.getUserRole());
        assertEquals(0.0, userResponse.getBalance(), "Mặc định constructor này phải gán số dư bằng 0.0");
    }

    //Test tất cả các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        UserResponseDTO userResponse = new UserResponseDTO();

        userResponse.setUserId(2);
        userResponse.setUserName("DaddyDung");
        userResponse.setUserRole(UserRole.SELLER);
        userResponse.setBalance(500.0);
        userResponse.setAuthStatus(AuthStatus.SUCCESS);
        userResponse.setMessage("Đăng nhập thành công!");

        assertEquals(2, userResponse.getUserId());
        assertEquals("DaddyDung", userResponse.getUserName());
        assertEquals(UserRole.SELLER, userResponse.getUserRole());
        assertEquals(500.0, userResponse.getBalance());
        assertEquals(AuthStatus.SUCCESS, userResponse.getAuthStatus());
        assertEquals("Đăng nhập thành công!", userResponse.getMessage());
    }
}