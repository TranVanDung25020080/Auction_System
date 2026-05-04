package com.auction.client.controller.bidderdashboard;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.productcard.ProductCardController;
import com.auction.client.service.http.GetAutionApi;
import com.auction.common.model.Auction.Auction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BidderDashboardController {
    //FXML fields
    @FXML
    private FlowPane productContainer;
    //other fields
    private int userId;


    @FXML
    public void initialize() {
        renderProducts();
    }

    private void renderProducts() {
        productContainer.getChildren().clear();
        try {
            //call api of get all auction:
            List<Auction> auctionList=new GetAutionApi().getAllAuction().getAuctionList();

            for (Auction auction:auctionList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/product_card.fxml"));
                Parent card = loader.load();

                // Lấy controller của từng Card
                ProductCardController controller = loader.getController();

                // Truyền ĐỦ 4 tham số: Tên, Giá, Đường dẫn ảnh, Số giây (ví dụ 3600)
                controller.setData(
                        auction.getItemName(),
                        String.valueOf(auction.getCurrentHighestPrice()),
                        "src/main/resources/com.auction.client/view/khanh.png",// chưa được
                        auction.getDurationLeft()
                );

                productContainer.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Method for other classes to call
}