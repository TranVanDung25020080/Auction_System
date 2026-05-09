package com.auction.client.controller.biddingpopup.buttonhandler;

import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.client.service.AuctionRoomService;
import com.auction.client.service.BidService;
import com.auction.common.model.Auction.Auction;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NormalBidButton {
    public void handle(TextField txtNormalBid, Auction currentAuction, Label lblStatus,
                       BiddingPopupController biddingPopupController){
        try {
            double amount = Double.parseDouble(txtNormalBid.getText());
            if (amount > currentAuction.getCurrentPrice()) {
                lblStatus.setText("Đã đặt thầu: " + amount);
                // TODO: Gửi yêu cầu bid lên Server ở đây

                new BidService().sendBid(biddingPopupController,amount);



            } else {
                lblStatus.setText("Giá thầu phải cao hơn giá hiện tại!");
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Vui lòng nhập số tiền hợp lệ!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
