package com.auction.client.controller.biddingpopup.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.auctionhistory.AuctionHistoryController;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.http.UserApi;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.enums.GetBidInfoType;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.Auction.BidTransaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewBidHistoryButton {
    public void handle(BiddingPopupController biddingPopupController){
        Auction auction=biddingPopupController.getCurrentAuction();

        try {
            // 1. Load file FXML vừa tạo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/auction_history_popup.fxml"));
            Parent root = loader.load();
            AuctionHistoryController auctionHistoryController =loader.getController();

            //Call api va truyen du lieu cho auction history card:
            int bidderId=biddingPopupController.getUserId();
            int auctionId=auction.getAuctionId();

            GetBidInfoRequestDTO getBidInfoRequestDTO=new GetBidInfoRequestDTO();
            getBidInfoRequestDTO.setBidderId(bidderId);
            getBidInfoRequestDTO.setAuctionId(auctionId);
            getBidInfoRequestDTO.setGetBidInfoType(GetBidInfoType.BIDDER_ID);

            GetBidInfoResponseDTO getBidInfoResponseDTO=new UserApi().getBidInfoByBidderId(getBidInfoRequestDTO);

            List<BidTransaction> bidTransactionList=getBidInfoResponseDTO.getBidTransactionList();

            if (bidTransactionList.size()!=0){
                auctionHistoryController.setAuctionData(auction.getItemName(),bidTransactionList);

                // 2. Tạo Stage mới (Cửa sổ mới)
                Stage stage = new Stage();
                stage.setTitle("Lịch sử đấu giá - " + auction.getItemName());

                // Làm cho cửa sổ này ở trên cùng, phải đóng nó mới bấm được Dashboard
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setScene(new Scene(root));
                stage.show();

                System.out.println("Đã mở cửa sổ lịch sử cho: " + auction.getItemName());


            }
            else{
                Alert.showAlert("ERROR","There has never been anyone who has bidded this auction yet!");
            }




        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("ERROR",e.getMessage());
        }

    }

}
