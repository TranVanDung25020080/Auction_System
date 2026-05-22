package com.auction.client.controller.bidderwallet.buttonhandler;

import com.auction.client.controller.bidderwallet.BidderWalletController;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CloseButton {
    public void handle(BidderWalletController bidderWalletController){
        Label lblBalance=bidderWalletController.getLblBalance();

        // Lấy Stage và đóng cửa sổ
        Stage stage = (Stage) lblBalance.getScene().getWindow();
        stage.close();
    }
}
