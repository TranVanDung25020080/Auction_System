package com.auction.client.controller.biddingpopup.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AutoBidButton {
    public void handle(TextField txtMaxBid, TextField txtIncrement, Label lblStatus,
                       Auction currentAuction){
        AuctionStatus status=currentAuction.getStatus();

        if (status==AuctionStatus.PENDING || status==AuctionStatus.OPEN){

            try {
                double maxBid = Double.parseDouble(txtMaxBid.getText());
                double increment = Double.parseDouble(txtIncrement.getText());

                lblStatus.setText("Đã kích hoạt Auto-Bid: Max " + maxBid);
                // TODO: Gửi yêu cầu Auto-bid lên Server ở đây


            } catch (NumberFormatException e) {
                lblStatus.setText("Vui lòng nhập thông số Auto-bid hợp lệ!");
            }

        }
        else {

            Alert.showAlert("ERROR","This auction has ended!");

        }


    }
}
