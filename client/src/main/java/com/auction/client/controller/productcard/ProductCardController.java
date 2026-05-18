package com.auction.client.controller.productcard;

// 1. CHÚ Ý: Import đúng class Auction từ common.model!
import com.auction.client.controller.productcard.buttonhandler.JoinRoomButton;
import com.auction.common.model.Auction.Auction;

import com.auction.common.model.User.Bidder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ProductCardController {
    //FXML Fields:
    @FXML private ImageView imgProduct;
    @FXML private Label lblProductName;
    @FXML private Label lblPrice;
    @FXML private Label lblTimer;
    @FXML private Button joinRoomButton;
    //Other Fields:
    private Auction auctionData;
    private int remainingSeconds;
    private Timeline timeline;
    private int userId;
    private Bidder bidder;
    //
    public void initialize(){
        this.joinRoomButton.setOnAction(event ->
                new JoinRoomButton().handle(this.auctionData,userId,this.bidder));
    }

    //Method for other classes to call:
    public void setData(Auction auction, String imagePath,int userId) {
        this.auctionData = auction;
        this.userId=userId; // LƯU LẠI ĐỐI TƯỢNG ĐỂ DÙNG KHI BID

        // Lấy tên từ Item thay vì getItemName()
        if (lblProductName != null) lblProductName.setText(auction.getItemName());

        // Gọi getCurrentPrice() thay vì getCurrentHighestPrice()
        if (lblPrice != null) lblPrice.setText(auction.getCurrentHighestPrice() + " VNĐ");

        // Tạm thời fix cứng số giây hoặc bạn phải viết hàm tính khoảng cách từ (bây giờ) đến (endTime)
        this.remainingSeconds = auction.getDurationLeft();
        if (imgProduct != null && imagePath != null) {
            try {
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                imgProduct.setImage(image);
            } catch (Exception e) {
                System.err.println("Lỗi nạp ảnh: " + imagePath);
            }
        }

        startTimer();
    }

    private void startTimer() {
        if (lblTimer == null) return;

        if (timeline != null) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingSeconds--;
            if (remainingSeconds <= 0) {
                lblTimer.setText("Hết giờ");
                if (joinRoomButton != null) joinRoomButton.setDisable(true);
                timeline.stop();
            } else {
                int h = remainingSeconds / 3600;
                int m = (remainingSeconds % 3600) / 60;
                int s = remainingSeconds % 60;
                lblTimer.setText(String.format("%02d:%02d:%02d", h, m, s));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void setBidder(Bidder bidder){
        this.bidder=bidder;
    }



}