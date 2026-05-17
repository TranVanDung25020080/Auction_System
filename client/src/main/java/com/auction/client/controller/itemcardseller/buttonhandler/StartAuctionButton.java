package com.auction.client.controller.itemcardseller.buttonhandler;

import com.auction.client.controller.itemcardseller.ItemCardSellerController;
import com.auction.client.controller.openauctionpopup.OpenAuctionPopupController;
import com.auction.common.model.Item.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartAuctionButton {
    @FXML
    public void handle(ItemCardSellerController itemCardSellerController, RemoveCardButton removeCardButton) throws IOException {
        Item item = itemCardSellerController.getItem();
        int sellerId = itemCardSellerController.getSellerId();
        if (item == null) return;

        try {
            // 1. Load FXML của Popup mở đấu giá
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/open_auction_popup.fxml"));
            Parent root = loader.load();

            // 2. Truyền dữ liệu Item sang Popup Controller
            OpenAuctionPopupController controller = loader.getController();
            controller.setItemData(item, sellerId);

            // 3. Hiển thị cửa sổ dạng Modal (bắt buộc xử lý xong mới quay lại được)
            Stage stage = new Stage();
            stage.setTitle("Thiết lập đấu giá: " + item.getName());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // 4. Nếu người dùng xác nhận "Bắt đầu" trên Popup thì mới xóa Card khỏi kho
            if (controller.isConfirmed()) {
                removeCardButton.removeCardFromUI(itemCardSellerController);
                System.out.println("Sản phẩm đã được đưa lên sàn đấu giá.");
            }

        } catch (IOException e) {
            System.err.println("Lỗi mở popup mở đấu giá: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
