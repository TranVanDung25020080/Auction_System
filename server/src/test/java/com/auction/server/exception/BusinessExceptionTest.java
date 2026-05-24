package com.auction.server.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    //Test InvalidBidException: Khách hàng đặt giá thấp hơn giá hiện tại hoặc phiên đã kết thúc (FINISHED)
    @Test
    void testInvalidBidException_WithMessage() {
        String expectedMessage = "Lỗi: Giá đặt vào phải lớn hơn giá cao nhất hiện tại!";

        InvalidBidException exception = new InvalidBidException(expectedMessage);

        assertNotNull(exception, "Lỗi: Không thể khởi tạo đối tượng InvalidBidException!");
        assertEquals(expectedMessage, exception.getMessage(),
                "Lỗi: Thông điệp lỗi lưu trong InvalidBidException bị sai!");
    }

    //Test AuctionNotFoundException: Người dùng truy cập hoặc tham gia vào phòng đấu giá (RoomId) không tồn tại
    @Test
    void testAuctionNotFoundException_WithMessage() {
        String expectedMessage = "Lỗi: Phiên đấu giá (RoomId) không tồn tại trên hệ thống!";

        AuctionNotFoundException exception = new AuctionNotFoundException(expectedMessage);

        assertNotNull(exception, "Lỗi: Không thể khởi tạo đối tượng AuctionNotFoundException!");
        assertEquals(expectedMessage, exception.getMessage(),
                "Lỗi: Thông điệp lỗi lưu trong AuctionNotFoundException không chính xác!");
    }

    //Test UserNotFoundException: Khi truy cập hoặc nạp tiền vào một tài khoản có ID không tồn tại
    @Test
    void testUserNotFoundException_WithMessage() {
        String expectedMessage = "Lỗi: Không tìm thấy tài khoản người dùng trong hệ thống!";

        UserNotFoundException exception = new UserNotFoundException(expectedMessage);

        assertNotNull(exception, "Lỗi: Không thể khởi tạo đối tượng UserNotFoundException!");
        assertEquals(expectedMessage, exception.getMessage(),
                "Lỗi: Thông điệp lỗi lưu trong UserNotFoundException bị sai lệch!");
    }

    //Test UnauthorizedException: Quyền BIDDER nhưng cố tình gọi các hàm của SELLER (Tạo sản phẩm, duyệt phiên)
    @Test
    void testUnauthorizedException_WithMessage() {
        String expectedMessage = "Lỗi: Bạn không có quyền thực hiện hành động này (Yêu cầu quyền SELLER)!";

        UnauthorizedException exception = new UnauthorizedException(expectedMessage);

        assertNotNull(exception, "Lỗi: Không thể khởi tạo đối tượng UnauthorizedException!");
        assertEquals(expectedMessage, exception.getMessage(),
                "Lỗi: Thông điệp lỗi lưu trong UnauthorizedException không đúng!");
    }

    //Kiểm thử bổ sung: Cấu trúc bọc nguyên nhân lỗi gốc (Cause) của ngoại lệ nghiệp vụ nếu có
    @Test
    void testBusinessExceptionWithCause() {
        String customMessage = "Giao dịch xử lý nghiệp vụ thất bại!";
        IllegalArgumentException rootCause = new IllegalArgumentException("Invalid argument status");

        InvalidBidException exception = new InvalidBidException(customMessage, rootCause);

        assertNotNull(exception);
        assertEquals(customMessage, exception.getMessage());
        assertNotNull(exception.getCause(), "Lỗi: Không tìm thấy nguyên nhân gốc Throwable cause!");
        assertEquals(rootCause, exception.getCause());
    }

    //Test BusinessException với Constructor chứa thông báo lỗi đơn thuần
    @Test
    void testBaseBusinessException_WithMessage() {
        String expectedMessage = "Lỗi nghiệp vụ hệ thống đấu giá!";
        BusinessException exception = new BusinessException(expectedMessage);

        assertNotNull(exception, "Lỗi: Không thể khởi tạo đối tượng BusinessException!");
        assertEquals(expectedMessage, exception.getMessage(), "Lỗi: Thông điệp nghiệp vụ bị sai lệch!");
        assertNull(exception.getErrorCode(), "Lỗi: errorCode phải bằng null khi không truyền vào constructor!");
    }

    //Test BusinessException với đầy đủ thông báo lỗi và mã lỗi
    @Test
    void testBaseBusinessException_WithMessageAndErrorCode() {
        String expectedMessage = "Tài khoản của bạn đã bị khóa!";
        String expectedErrorCode = "ERR_USER_BANNED";
        BusinessException exception = new BusinessException(expectedMessage, expectedErrorCode);

        assertNotNull(exception, "Lỗi: Không thể khởi tạo đối tượng BusinessException!");
        assertEquals(expectedMessage, exception.getMessage(), "Lỗi: Thông điệp lỗi lưu trong Exception không đúng!");
        assertEquals(expectedErrorCode, exception.getErrorCode(), "Lỗi: Mã lỗi errorCode bốc ra bị sai lệch!");
    }
}