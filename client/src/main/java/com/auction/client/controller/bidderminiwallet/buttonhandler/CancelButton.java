package com.auction.client.controller.bidderminiwallet.buttonhandler;

import com.auction.client.controller.bidderminiwallet.BidderMiniWalletController;
import javafx.stage.Stage;

public class CancelButton {

    public void handle(BidderMiniWalletController controller) {
        // Hủy bỏ giá trị hạn mức
        controller.setChosenMaxBid(-1);

        // Đóng cửa sổ hiện tại
        Stage stage = (Stage) controller.getCancelButton().getScene().getWindow();
        stage.close();
    }
}