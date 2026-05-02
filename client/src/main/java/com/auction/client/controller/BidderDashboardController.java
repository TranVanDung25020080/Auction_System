package com.auction.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import java.io.IOException;

public class BidderDashboardController {

    @FXML
    private FlowPane productContainer;

    @FXML
    public void initialize() {
        renderProducts();
    }

    private void renderProducts() {
        productContainer.getChildren().clear();
        try {
            for (int i = 1; i <= 9; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/product_card.fxml"));
                Parent card = loader.load();

                // Lấy controller của từng Card
                ProductCardController controller = loader.getController();

                // Truyền ĐỦ 4 tham số: Tên, Giá, Đường dẫn ảnh, Số giây (ví dụ 3600)
                controller.setData(
                        "Sản phẩm xịn " + i,
                        "500.000",
                        "src/main/resources/com.auction.client/view/khanh.png",// chưa được
                        3600 + (i * 100)
                );

                productContainer.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}    // Đảm bảo có đủ dấu đóng ngoặc ở đây