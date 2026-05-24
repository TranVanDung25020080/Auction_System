package com.auction.server.exception;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseExceptionTest {
    @Test
    //Kiểm thử Constructor nhận vào một chuỗi thông báo lỗi (String message)
    void testDatabaseExceptionWithMessage() {
        String expectedMessage = "Lỗi kết nối database!";

        DatabaseException exception = new DatabaseException(expectedMessage);

        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage(),
                "Lỗi: Thông điệp lỗi lưu trong DatabaseException không đúng!");
    }

    @Test
    //Kiểm thử Constructor bọc lỗi gốc (String message, Throwable cause)
    void testDatabaseExceptionWithMessageAndCause() {
        String customMessage = "Hệ thống không thể truy vấn bảng User!";

        SQLException rootCause = new SQLException("Access denied for user 'root'@'localhost'");

        DatabaseException exception = new DatabaseException(customMessage, rootCause);

        assertNotNull(exception);
        assertEquals(customMessage, exception.getMessage(), "Lỗi: Sai thông điệp tùy biến!");

        assertNotNull(exception.getCause(), "Lỗi: Không tìm thấy nguyên nhân lỗi gốc!");
        assertEquals(rootCause, exception.getCause(), "Lỗi: Nguyên nhân gốc bị sai lệch!");
    }
}