package com.auction.client.controller.productcard.buttonhandler;

import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.common.model.Auction.Auction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinRoomButton {
    public void handle(Auction auctionData){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidding_popup.fxml"));
            Parent root = loader.load();

            // Sửa lại theo đúng package chứa BiddingPopupController (nếu cần)
            BiddingPopupController popupController = loader.getController();
            popupController.initData(auctionData);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
/*            stage.initModality(Modality.APPLICATION_MODAL);*/
            stage.setTitle("Chi tiết phiên đấu giá");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi mở Popup: " + e.getMessage());
        }

    }
}
