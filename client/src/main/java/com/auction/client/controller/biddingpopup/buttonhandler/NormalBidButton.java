package com.auction.client.controller.biddingpopup.buttonhandler;

import com.auction.common.model.Auction.Auction;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NormalBidButton {
    public void handle(TextField txtNormalBid, Auction currentAuction, Label lblStatus){
        try {
            double amount = Double.parseDouble(txtNormalBid.getText());
            if (amount > currentAuction.getCurrentPrice()) {
                lblStatus.setText("Đã đặt thầu: " + amount);
                // TODO: Gửi yêu cầu bid lên Server ở đây
            } else {
                lblStatus.setText("Giá thầu phải cao hơn giá hiện tại!");
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Vui lòng nhập số tiền hợp lệ!");
        }
    }
}
