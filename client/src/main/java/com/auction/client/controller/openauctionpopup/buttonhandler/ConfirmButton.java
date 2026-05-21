package com.auction.client.controller.openauctionpopup.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.openauctionpopup.OpenAuctionPopupController;
import com.auction.client.network.http.AuctionApi;
import com.auction.common.dto.response.CreateAuctionResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.Item.Item;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConfirmButton {
    @FXML
    public void handle(OpenAuctionPopupController openAuctionPopupController) {
        try {
            double startPrice = Double.parseDouble(openAuctionPopupController.getTxtStartPrice().getText());
            int sellerId=openAuctionPopupController.getSellerId();
            String itemName=openAuctionPopupController.getItemName();

            Item item=openAuctionPopupController.getItem();
            int itemId=0;
            if (item!=null) {itemId= item.getId();}

            LocalDateTime start=LocalDateTime.now();
            LocalDateTime end = LocalDateTime.of(openAuctionPopupController.getDpEndDate().getValue(), LocalTime.parse(openAuctionPopupController.getTxtEndTime().getText()));

            if (end.isBefore(start)) {
                Alert.showAlert("ERROR", "Thời gian kết thúc phải sau khi bắt đầu!");
                return;
            }
            //Call Api:
            Auction auction=new Auction(itemId,sellerId,itemName,startPrice,start,end);


            CreateAuctionResponseDTO createAuctionResponseDTO=new AuctionApi().createAuction(auction);

            Alert.showAlert("Announcement", createAuctionResponseDTO.getMessage());

            //
            openAuctionPopupController.setConfirmed(true);
            // Dong popup
            ((Stage) openAuctionPopupController.getLblItemName().getScene().getWindow()).close();

        } catch (Exception e) {
            Alert.showAlert("ERROR",e.getMessage());
        }
    }
}
