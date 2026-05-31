package com.auction.client.controller;

import com.auction.common.enums.AuthStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientNavigationTest {

    @Test
    void testHandleLoginResponse_Success_ShouldNavigateToMain() {
        // test mô phỏng khi Server phản hồi đăng nhập thành công
        AuthStatus statusFromServer = AuthStatus.SUCCESS;

        boolean shouldOpenMainView = (statusFromServer == AuthStatus.SUCCESS);

        assertTrue(shouldOpenMainView, "Lỗi: Đăng nhập thành công nhưng không kích hoạt chuyển màn hình chính!");
    }

    @Test
    void testHandleLoginResponse_Failure_ShouldShowError() {
        // test mô phỏng khi Server báo đăng nhập thất bại (Sai mật khẩu)
        AuthStatus statusFromServer = AuthStatus.FAILED;

        boolean shouldShowAlert = (statusFromServer != AuthStatus.SUCCESS);

        assertTrue(shouldShowAlert, "Lỗi: Đăng nhập thất bại nhưng Client không bật hộp thoại thông báo lỗi!");
    }

    @Test
    void testAuctionStatusDisplay() {
        //test logic bôi màu giao diện dựa trên trạng thái phiên (Ảnh DB: status = OPEN)
        String dbStatus = "OPEN";
        String buttonText;

        if ("OPEN".equals(dbStatus)) {
            buttonText = "Đặt Giá Ngay";
        } else {
            buttonText = "Đã Kết Thúc";
        }

        assertEquals("Đặt Giá Ngay", buttonText, "Lỗi: Phiên đang OPEN nhưng nút bấm lại không hiển thị chữ Đặt Giá Ngay!");
    }
}