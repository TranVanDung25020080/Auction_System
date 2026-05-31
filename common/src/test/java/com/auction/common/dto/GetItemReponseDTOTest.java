package com.auction.common.dto;

import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetItemReponseDTOTest {

    //Test Constructor không tham số
    @Test
    void testDefaultConstructor() {
        GetItemReponseDTO response = new GetItemReponseDTO();

        assertEquals(0, response.getSellerId(), "Lỗi: Mặc định sellerId phải bằng 0!");
        assertNull(response.getItemList(), "Lỗi: Mặc định itemList phải bằng null!");
        assertNull(response.getStatus(), "Lỗi: Mặc định status phải bằng null!");
        assertNull(response.getMessage(), "Lỗi: Mặc định message phải bằng null!");
    }

    //Test Constructor có 2 tham số (sellerId, itemList)
    @Test
    void testConstructorWithParameters() {
        int expectedSellerId = 10;

        List<Item> mockList = new ArrayList<>();
        mockList.add(new Electronics("Iphone 16 promax", "Mượt", 1200.0, expectedSellerId, ItemStatus.AVAILABLE));
        mockList.add(new Electronics("Macbook Pro", "Màu xám", 2000.0, expectedSellerId, ItemStatus.AVAILABLE));

        GetItemReponseDTO response = new GetItemReponseDTO(expectedSellerId, mockList);

        assertEquals(expectedSellerId, response.getSellerId(), "Lỗi: sellerId bốc ra từ Constructor bị sai lệch!");
        assertNotNull(response.getItemList(), "Lỗi: itemList không được null khi khởi tạo qua Constructor!");
        assertEquals(2, response.getItemList().size(), "Lỗi: Số lượng phần tử trong danh sách bị sai lệch!");
        assertEquals("Iphone 16 promax", response.getItemList().get(0).getName(), "Lỗi: Tên vật phẩm đầu tiên bị sai!");
    }

    //Test toàn bộ các hàm Setter và Getter
    @Test
    void testSettersAndGetters() {
        GetItemReponseDTO response = new GetItemReponseDTO();

        int sellerId = 5;
        String message = "Tải danh sách sản phẩm thành công";
        AuthStatus status = AuthStatus.SUCCESS;
        List<Item> mockList = new ArrayList<>();
        mockList.add(new Electronics("iPad Pro M4", "Màn hình OLED", 1100.0, sellerId, ItemStatus.AVAILABLE));

        response.setSellerId(sellerId);
        response.setMessage(message);
        response.setItemList(mockList);
        response.setStatus(status);

        assertEquals(sellerId, response.getSellerId(), "Lỗi: Hàm setSellerId hoặc getSellerId hoạt động sai!");
        assertEquals(message, response.getMessage(), "Lỗi: Hàm setMessage hoặc getMessage hoạt động sai!");
        assertEquals(mockList, response.getItemList(), "Lỗi: Hàm setItemList hoặc getItemList hoạt động sai!");
        assertEquals(status, response.getStatus(), "Lỗi: Hàm setStatus hoặc getStatus hoạt động sai!");
    }
}