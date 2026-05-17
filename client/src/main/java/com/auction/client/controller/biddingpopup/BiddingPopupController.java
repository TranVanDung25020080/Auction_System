package com.auction.client.controller.biddingpopup;

import com.auction.client.controller.biddingpopup.buttonhandler.AutoBidButton;
import com.auction.client.controller.biddingpopup.buttonhandler.ExitButton;
import com.auction.client.controller.biddingpopup.buttonhandler.NormalBidButton;
import com.auction.client.network.socket.ClientSocket;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.model.Auction.Auction;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import java.time.LocalDateTime;

public class BiddingPopupController {

    //FXML Fields:
    @FXML private Label lblId, lblName, lblBasePrice, lblCurrentPrice, lblCountdown, lblStatus,lblAnnouncement;
    @FXML private TextField txtNormalBid;
    @FXML private TextField txtMaxBid;      // fx:id phải khớp trong FXML
    @FXML private TextField txtIncrement;   // fx:id phải khớp trong FXML
    @FXML private Button normalBidButton,autoBidButton,exitButton;
    //Other fields:
    private Auction currentAuction;
    private Timeline timeline;
    private BidUpdateResponseDTO bidUpdateResponseDTO;
    private ClientSocket clientSocket;
    private int userId;
    //init method which is gonna be called automaticly:
    public void initialize(){
        //set on action for buttons:
        this.normalBidButton.setOnAction(event ->
                new NormalBidButton().handle(this.txtNormalBid,this.currentAuction,this.lblStatus,
                        this));

        this.autoBidButton.setOnAction(event ->
                new AutoBidButton().handle(this.txtMaxBid,this.txtIncrement,this.lblStatus,this.currentAuction,this));

        this.exitButton.setOnAction(event ->
                new ExitButton().handle(this.timeline,this.lblId));
        //Khoi tao doi tuong dto :
        this.bidUpdateResponseDTO=new BidUpdateResponseDTO();
    }


    //Method for other classes to call:
    public void initData(Auction auction) {
        this.currentAuction = auction;

        // 1. Gán dữ liệu (Sử dụng String.valueOf để tránh lỗi int/double)
        lblId.setText(String.valueOf(auction.getAuctionId()));
        lblName.setText(auction.getItemName());
        lblBasePrice.setText(String.valueOf(auction.getStartPrice()));
        lblCurrentPrice.setText(String.valueOf(auction.getCurrentPrice()));

        // 2. Chạy đồng hồ đếm ngược
        if (auction.getEndTime() != null) {
            startCountdown(auction.getEndTime());
        } else {
            lblCountdown.setText("N/A");
        }
    }

    private void startCountdown(LocalDateTime endTime) {
        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            java.time.Duration duration = java.time.Duration.between(LocalDateTime.now(), endTime);

            if (duration.isNegative() || duration.isZero()) {
                lblCountdown.setText("Đã kết thúc");
                timeline.stop();
            } else {
                long hours = duration.toHours();
                int minutes = duration.toMinutesPart();
                int seconds = duration.toSecondsPart();
                lblCountdown.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void updateHighCurrentPrice(double newPrice){
        this.currentAuction.setCurrentHighestPrice(newPrice);
        this.bidUpdateResponseDTO.setNewHighestPrice(newPrice);
    }
    public void setLabelAnnoucement(String text){
        this.lblAnnouncement.setText(text);
    }
    public void setLabelCurrentPrice(){
        this.lblCurrentPrice.setText(String.valueOf(this.currentAuction.getCurrentHighestPrice()));
    }
    public void setLblStatus(String text){
        this.lblStatus.setText(text);
    }
    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }
    public ClientSocket getClientSocket(){
        return this.clientSocket;
    }
    public Auction getCurrentAuction(){
        return this.currentAuction;
    }
    public BidUpdateResponseDTO getBidUpdateResponseDTO(){
        return this.bidUpdateResponseDTO;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void endAuction(){
        this.currentAuction.endAuction();
    }
    public void startAuction(){
        this.currentAuction.startAuction();
    }
}