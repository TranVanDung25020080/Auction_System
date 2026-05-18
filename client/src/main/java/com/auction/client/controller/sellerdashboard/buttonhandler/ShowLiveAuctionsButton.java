package com.auction.client.controller.sellerdashboard.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.auctioncardseller.AuctionCardSellerController;
import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.client.network.http.AuctionApi;
import com.auction.common.dto.request.GetAuctionRequestDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.model.Auction.Auction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class ShowLiveAuctionsButton {
    public void handle(SellerDashboardController sellerDashboardController) throws IOException {
        Label lblHeader=sellerDashboardController.getLblHeader();
        FlowPane flowPaneContent=sellerDashboardController.getFlowPaneContent();

        lblHeader.setText("PHIÊN ĐẤU GIÁ ĐANG CHẠY");
        flowPaneContent.getChildren().clear();

/*        // Tạo 1 Auction giả để test đồng hồ và giá
        Auction mockAuction = new Auction(
                101, 1, 2, 1200000, 1,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5), // Kết thúc sau 5 phút
                AuctionStatus.OPEN,
                "Tranh Sơn Dầu Phố Cổ"
        );*/
        //Goi auctionList tu server here:
        int sellerId=sellerDashboardController.getSellerId();
        GetAuctionRequestDTO getAuctionRequestDTO=new GetAuctionRequestDTO(sellerId);

        GetAuctionResponseDTO getAuctionResponseDTO=new AuctionApi().getAuctionBySellerId(getAuctionRequestDTO);
        System.out.println(getAuctionResponseDTO.getMessage());

        List<Auction> auctionList=getAuctionResponseDTO.getAuctionList();



        try {
            for (Auction auction:auctionList){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/auction_card_seller.fxml"));
                VBox card = loader.load();

                // Lấy controller của Auction Card
                AuctionCardSellerController controller = loader.getController();
                controller.setAuctionData(auction);

                flowPaneContent.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("ERROR",e.getMessage());
        }
    }
}
