package com.auction.client.controller.biddingpopup.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.client.service.AuctionRoomService;
import com.auction.client.service.BidService;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.User.Bidder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NormalBidButton {
    public void handle(TextField txtNormalBid, Auction currentAuction, Label lblStatus,
                       BiddingPopupController biddingPopupController){
        AuctionStatus status=currentAuction.getStatus();
        Bidder bidder=biddingPopupController.getBidder();

        if (status==AuctionStatus.PENDING || status==AuctionStatus.OPEN){

            try {
                double amount = Double.parseDouble(txtNormalBid.getText());
                if (amount > currentAuction.getCurrentPrice()/* && amount <=bidder.getBalance()*/) {

                    // TODO: Gửi yêu cầu bid lên Server ở đây:
                    if (amount <= biddingPopupController.getMaxBidDuringAuction()){

                        new BidService().sendBid(biddingPopupController,amount);

                    }
                    else{

                        Alert.showAlert("ERROR","Your bidAmount must be less than your max bid that you chose!");
                    }


                } else {
                    Alert.showAlert("ERROR","Your bidAmount must be strictly greater than the highest current price!");
                }
            } catch (NumberFormatException e) {
                Alert.showAlert("ERROR","Vui lòng nhập số tiền hợp lệ!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else {

            Alert.showAlert("ERROR","This auction has ended!");

        }

    }
}
