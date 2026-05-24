package com.auction.common.dto;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDTOTest {

    //test cons truyền vào 5 tham số
    @Test
    void testConstructorFiveParameters() {
        int expectedId = 1;
        String expectedOwner = "nguoitinhmuadong";
        String expectedUserName = "Htamhuhu";
        UserRole expectedRole = UserRole.BIDDER;
        double expectedBalance = 1000000000.0;

        UserResponseDTO userResponse = new UserResponseDTO(expectedId, expectedOwner, expectedUserName, expectedRole, expectedBalance);

        assertEquals(expectedId, userResponse.getUserId(), "Lỗi: Khởi tạo hoặc lấy dữ liệu userId bị sai!");
        assertEquals(expectedOwner, userResponse.getOwnerName(), "Lỗi: Khởi tạo hoặc lấy dữ liệu ownerName bị sai!");
        assertEquals(expectedUserName, userResponse.getUserName(), "Lỗi: Khởi tạo hoặc lấy dữ liệu userName bị sai!");
        assertEquals(expectedRole, userResponse.getUserRole(), "Lỗi: Khởi tạo hoặc lấy dữ liệu userRole bị sai!");
        assertEquals(expectedBalance, userResponse.getBalance(), "Lỗi: Khởi tạo hoặc lấy dữ liệu balance bị sai!");
    }

    //Test cons truyền vào 3 tham số
    @Test
    void testConstructorThreeParameters() {
        String expectedOwnerName = "Htam testAdmin";
        String expectedUserName = "admin_1779641577050";
        UserRole expectedRole = UserRole.ADMIN;

        UserResponseDTO userResponse = new UserResponseDTO(expectedOwnerName, expectedUserName, expectedRole);

        assertEquals(expectedOwnerName, userResponse.getOwnerName(), "Lỗi: Constructor 3 tham số gán sai ownerName!");
        assertEquals(expectedUserName, userResponse.getUserName(), "Lỗi: Constructor 3 tham số gán sai userName!");
        assertEquals(expectedRole, userResponse.getUserRole(), "Lỗi: Constructor 3 tham số gán sai userRole!");
        assertEquals(0.0, userResponse.getBalance(), "Lỗi: Mặc định số dư tài khoản mới phải bằng 0.0!");
    }

    //Test Constructor mặc định và các hàm Setter/Getter
    @Test
    void testSettersAndGetters() {
        UserResponseDTO userResponse = new UserResponseDTO();

        userResponse.setAuthStatus(AuthStatus.SUCCESS);
        userResponse.setMessage("Đăng nhập thành công!");

        assertEquals(AuthStatus.SUCCESS, userResponse.getAuthStatus(), "Lỗi: Hàm setAuthStatus hoặc getAuthStatus chạy sai!");
        assertEquals("Đăng nhập thành công!", userResponse.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage chạy sai!");

        assertEquals(0, userResponse.getUserId());
        assertNull(userResponse.getUserName());
        assertNull(userResponse.getOwnerName());
        assertNull(userResponse.getUserRole());
        assertEquals(0.0, userResponse.getBalance());
    }
}