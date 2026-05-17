package com.auction.client.controller.biddingpopup.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.service.BidService;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AutoBidButton {
    public void handle(TextField txtMaxBid, TextField txtIncrement, Label lblStatus,
                       Auction currentAuction, BiddingPopupController biddingPopupController){
        AuctionStatus status=currentAuction.getStatus();

        if (status==AuctionStatus.PENDING || status==AuctionStatus.OPEN){

            try {
                double maxBid = Double.parseDouble(txtMaxBid.getText());
                double increment = Double.parseDouble(txtIncrement.getText());

                if (maxBid>currentAuction.getCurrentHighestPrice()){

                    lblStatus.setText("Đã kích hoạt Auto-Bid: Max " + maxBid + "Muc tang: " + increment);
                    // TODO: Gửi yêu cầu Auto-bid lên Server ở đây
                    new BidService().startAutoBidding(biddingPopupController,maxBid,increment);

                }
                else {
                    Alert.showAlert("ERROR","Your maxBid must be strictly greater than the current highest price");
                }


            } catch (NumberFormatException e) {
                lblStatus.setText("Vui lòng nhập thông số Auto-bid hợp lệ!");
            } catch (IOException e) {
                e.printStackTrace();
                Alert.showAlert("ERROR",e.getMessage());
            }

        }
        else {

            Alert.showAlert("ERROR","This auction has ended!");

        }


    }
}
