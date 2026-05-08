package com.auction.client.controller.bidderdashboard;

import com.auction.client.controller.productcard.ProductCardController;
import com.auction.common.model.Auction.Auction; // Đảm bảo import bản client
import com.auction.client.service.http.GetAutionApi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import java.util.List;

public class BidderDashboardController {
    @FXML private FlowPane productContainer;
    private int userId;

    public void setUserId(int userId) { // Fix lỗi LoginButton
        this.userId = userId;
    }

    @FXML
    public void initialize() {
        renderProducts();
    }

    private void renderProducts() {
        productContainer.getChildren().clear();
        try {
            var response = new GetAutionApi().getAllAuction();
            // Sau khi đổi import, dòng này sẽ chạy mượt mà:
            List<Auction> auctionList = (List<Auction>) (Object) response.getAuctionList();

            for (Auction auction : auctionList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/product_card.fxml"));
                Parent card = loader.load();
                ProductCardController controller = loader.getController();

                // Bây giờ auction đã là bản common, card sẽ nhận được dữ liệu
                controller.setData(auction, "/com/auction/client/view/khanh.png");
                productContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log sẽ không còn ClassCastException nữa
        }
    }
}