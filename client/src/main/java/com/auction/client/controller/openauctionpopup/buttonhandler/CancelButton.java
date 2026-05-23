package com.auction.client.controller.openauctionpopup.buttonhandler;

import com.auction.client.controller.openauctionpopup.OpenAuctionPopupController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CancelButton {
    @FXML
    public void handle(OpenAuctionPopupController openAuctionPopupController) {
        openAuctionPopupController.setConfirmed(false);
        ((Stage) openAuctionPopupController.getLblItemName().getScene().getWindow()).close();
    }
}
