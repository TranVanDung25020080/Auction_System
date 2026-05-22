
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.db.DatabaseConnection;
import com.auction.server.exception.DatabaseException;
import com.auction.server.auth.LoginService;
import com.auction.server.auth.SignUpService;

public class TestApp {
    public static void main(String[] args) {
        // Khởi tạo các Service
        LoginService loginService = new LoginService();
        SignUpService signUpService = new SignUpService();
        AuctionDAO aucdao = new AuctionDAO();

        try {
            System.out.println("========== BẮT ĐẦU TEST ==========\n");

             //---------------------------------------------------------
             //TEST 1: ĐĂNG KÝ NGƯỜI BÁN (SELLER)
             //---------------------------------------------------------
            System.out.println("1. Đang signup User...");
            UserResponseDTO tranVanDung = signUpService.signUp("TranVanDung", "DungChan", "123123", UserRole.SELLER);
            System.out.println("SignUp Success.");

            // ---------------------------------------------------------
            // TEST 2: THÊM VẬT PHẨM VÀO KHO
            // ---------------------------------------------------------
//            System.out.println("2. Đang test xem vật phẩm của seller TranVanDung...");
//            System.out.println("Success.");
//            List<Item> items = itemService.getItemBySellerId(sellerTranVanDung);
//            for (Item item : items) {
//                System.out.println(" - ID: " + item.getId()
//                        + " | Tên: " + item.getName()
//                        + " | Giá gốc: $" + item.getInitialPrice()
//                        + " | Loại: " + item.getClass().getSimpleName()
//                        + " | Trạng thái: " + item.getItem_status());

                // ---------------------------------------------------------
                // TEST 3: LẤY DANH SÁCH TỪ DATABASE LÊN
                // ---------------------------------------------------------
//            System.out.println("3. Đang test tải danh sách kho hàng...");
//            List<Item> khoHang = itemService.getAllAvailableItems();
//
//            System.out.println("📦 KẾT QUẢ TRONG KHO CÓ " + khoHang.size() + " MÓN:");
//            for (Item item : khoHang) {
//                System.out.println(" - ID: " + item.getId()
//                        + " | Tên: " + item.getName()
//                        + " | Giá gốc: $" + item.getInitialPrice()
//                        + " | Loại: " + item.getClass().getSimpleName());
            }
            catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\n========== KẾT THÚC TEST ==========");
        DatabaseConnection.closeConnectionPool();
    }
}
