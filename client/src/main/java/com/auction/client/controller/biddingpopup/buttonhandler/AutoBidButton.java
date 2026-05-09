package com.auction.client.controller.biddingpopup.buttonhandler;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AutoBidButton {
    public void handle(TextField txtMaxBid, TextField txtIncrement, Label lblStatus){

        try {
            double maxBid = Double.parseDouble(txtMaxBid.getText());
            double increment = Double.parseDouble(txtIncrement.getText());

            lblStatus.setText("Đã kích hoạt Auto-Bid: Max " + maxBid);
            // TODO: Gửi yêu cầu Auto-bid lên Server ở đây


        } catch (NumberFormatException e) {
            lblStatus.setText("Vui lòng nhập thông số Auto-bid hợp lệ!");
        }

    }
}
