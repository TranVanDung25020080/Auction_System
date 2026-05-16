package com.auction.client.controller.sellerdashboard.buttonhandler;

import com.auction.client.controller.auctioncardseller.AuctionCardSellerController;
import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;

public class ShowLiveAuctionsButton {
    public void handle(SellerDashboardController sellerDashboardController){
        Label lblHeader=sellerDashboardController.getLblHeader();
        FlowPane flowPaneContent=sellerDashboardController.getFlowPaneContent();

        lblHeader.setText("PHIÊN ĐẤU GIÁ ĐANG CHẠY");
        flowPaneContent.getChildren().clear();

        // Tạo 1 Auction giả để test đồng hồ và giá
        Auction mockAuction = new Auction(
                101, 1, 2, 1200000, 1,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5), // Kết thúc sau 5 phút
                AuctionStatus.OPEN,
                "Tranh Sơn Dầu Phố Cổ"
        );
        //Goi auctionList tu server here:
        



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/auction_card_seller.fxml"));
            VBox card = loader.load();

            // Lấy controller của Auction Card
            AuctionCardSellerController controller = loader.getController();
            controller.setAuctionData(mockAuction);

            flowPaneContent.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
