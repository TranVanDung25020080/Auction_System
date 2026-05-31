package com.auction.common.dto;

import com.auction.common.dto.response.BaseResponse;
import com.auction.common.enums.ReponseType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseResponseTest {

    //Test Constructor mặc định và các giá trị khởi tạo ban đầu
    @Test
    void testDefaultConstructor() {
        BaseResponse response = new BaseResponse();

        // Ban đầu các thuộc tính protected chưa được nạp dữ liệu phải bằng null
        assertNull(response.getMessage(), "Lỗi: Mặc định trường message phải bằng null!");
        assertNull(response.getReponseType(), "Lỗi: Mặc định trường responseType phải bằng null!");

        // Hàm hiển thị thông báo mặc định của lớp cha phải trả về chuỗi rỗng
        assertEquals("", response.displayMessage(), "Lỗi: Hàm displayMessage() mặc định của lớp cha phải là chuỗi rỗng!");
    }

    // 2. Test các hàm Setter và Getter phối hợp (Bám sát hàm viết thiếu chữ 's' của em)
    @Test
    void testSettersAndGetters() {
        // Vì class con thường kế thừa BaseResponse, ta có thể test trực tiếp thông qua một instance ẩn danh hoặc lớp cha công khai
        BaseResponse response = new BaseResponse();

        String testMessage = "Hệ thống kết nối máy chủ thành công";

        // Thực thi hàm Setter từ code gốc của em
        response.setMessage(testMessage);

        // Xác thực giá trị trả về qua hàm Getter
        assertEquals(testMessage, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage hoạt động không chính xác!");
    }

    // 3. Test tính năng kế thừa vùng nhớ protected (Giả lập hành vi ghi đè dữ liệu của các DTO con)
    @Test
    void testProtectedFieldsAccess() {
        // Tạo một lớp con ẩn danh (Anonymous Class) để test khả năng đọc trường protected của BaseResponse
        BaseResponse subResponse = new BaseResponse() {
            {
                this.responseType = ReponseType.BID_UPDATE; // Thử nghiệm nạp vùng protected
                this.message = "Giao dịch thành công";
            }
        };

        // Bốc ngược dữ liệu ra thông qua hàm Getter công khai để chứng minh tính toàn vẹn dữ liệu
        assertEquals(ReponseType.BID_UPDATE, subResponse.getReponseType(), "Lỗi: Hàm getReponseType() trả về sai phân loại vùng protected!");
        assertEquals("Giao dịch thành công", subResponse.getMessage(), "Lỗi: Hàm getMessage() trả về sai chuỗi vùng protected!");
    }
}