package com.auction.client.controller.openauctionpopup.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.openauctionpopup.OpenAuctionPopupController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConfirmButton {
    @FXML
    public void handle(OpenAuctionPopupController openAuctionPopupController) {
        try {
            double startPrice = Double.parseDouble(openAuctionPopupController.getTxtStartPrice().getText());

            LocalDateTime start=LocalDateTime.now();
            LocalDateTime end = LocalDateTime.of(openAuctionPopupController.getDpEndDate().getValue(), LocalTime.parse(openAuctionPopupController.getTxtEndTime().getText()));

            if (end.isBefore(start)) {
                Alert.showAlert("ERROR", "Thời gian kết thúc phải sau khi bắt đầu!");
                return;
            }

            openAuctionPopupController.setConfirmed(true);
            // Dong popup
            ((Stage) openAuctionPopupController.getLblItemName().getScene().getWindow()).close();

        } catch (Exception e) {
            Alert.showAlert("ERROR",e.getMessage());
        }
    }
}
