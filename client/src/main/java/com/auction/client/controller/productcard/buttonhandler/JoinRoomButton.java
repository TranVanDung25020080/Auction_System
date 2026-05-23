package com.auction.client.controller.productcard.buttonhandler;

import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.client.service.AuctionRoomService;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.User.Bidder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinRoomButton {
    public void handle(Auction auctionData, int userId, Bidder bidder){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidding_popup.fxml"));
            Parent root = loader.load();

            // Sửa lại theo đúng package chứa BiddingPopupController (nếu cần)
            BiddingPopupController popupController = loader.getController();
            popupController.initData(auctionData);
            popupController.setUserId(userId);
            popupController.setBidder(bidder);

            popupController.startAuction();





            //Goi den service broadcast khi joinRoom:

            ClientSocket clientSocket =new AuctionRoomService().joinRoom(userId,auctionData.getAuctionId(),popupController);
            popupController.setClientSocket(clientSocket); //truyen vao clientsocket cho biddingpoupcontroller

            //Hien thi cho user:
            Stage stage = new Stage();
            stage.setScene(new Scene(root,1100,800));
/*            stage.initModality(Modality.APPLICATION_MODAL);*/

            stage.setResizable(true);
            stage.setMinWidth(900);
            stage.setMinHeight(700);

            stage.setTitle("Chi tiết phiên đấu giá");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi mở Popup: " + e.getMessage());
        }

    }
}
