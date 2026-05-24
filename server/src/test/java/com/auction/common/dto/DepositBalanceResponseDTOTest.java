package com.auction.common.dto;

import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.enums.AuthStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DepositBalanceResponseDTOTest {

    //Test Constructor mặc định
    @Test
    void testDefaultConstructor() {
        UserBalanceResponseDTO response = new UserBalanceResponseDTO();

        assertEquals(0, response.getUserId(), "Lỗi: Mặc định userId ban đầu phải bằng 0!");
        assertEquals(0.0, response.getCurrentBalance(), "Lỗi: Mặc định currentBalance ban đầu phải bằng 0.0!");
        assertNull(response.getAuthStatus(), "Lỗi: Mặc định authStatus ban đầu phải là null!");
        assertNull(response.getMessage(), "Lỗi: Mặc định message ban đầu phải là null!");
    }

    //Test Constructor 2 tham số
    @Test
    void testConstructorWithTwoParameters() {
        int expectedUserId = 5;
        double expectedBalance = 1500.50;

        UserBalanceResponseDTO response = new UserBalanceResponseDTO(expectedUserId, expectedBalance);

        assertEquals(expectedUserId, response.getUserId(), "Lỗi: Constructor gán sai thông tin userId!");
        assertEquals(expectedBalance, response.getCurrentBalance(), "Lỗi: Constructor gán sai thông tin currentBalance!");

        assertNull(response.getAuthStatus());
        assertNull(response.getMessage());
    }

    //Test tất cả các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        UserBalanceResponseDTO response = new UserBalanceResponseDTO();

        int testUserId = 12;
        double testBalance = 250.75;
        AuthStatus testStatus = AuthStatus.SUCCESS;
        String testMessage = "deposit balance successfully!";

        response.setUserId(testUserId);
        response.setCurrentBalance(testBalance);
        response.setAuthStatus(testStatus);
        response.setMessage(testMessage);

        assertEquals(testUserId, response.getUserId(), "Lỗi: Hàm setUserId hoặc getUserId chạy sai!");
        assertEquals(testBalance, response.getCurrentBalance(), "Lỗi: Hàm setCurrentBalance hoặc getCurrentBalance chạy sai!");
        assertEquals(testStatus, response.getAuthStatus(), "Lỗi: Hàm setAuthStatus hoặc getAuthStatus chạy sai!");
        assertEquals(testMessage, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage chạy sai!");
    }
}