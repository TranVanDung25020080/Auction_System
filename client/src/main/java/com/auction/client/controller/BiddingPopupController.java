package com.auction.client.controller;

import com.auction.common.model.Auction.Auction;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import java.time.LocalDateTime;

public class BiddingPopupController {

    // Các Label hiển thị thông tin
    @FXML private Label lblId, lblName, lblBasePrice, lblCurrentPrice, lblCountdown, lblStatus;

    // Các trường nhập liệu cho thầu thường và tự động
    @FXML private TextField txtNormalBid;
    @FXML private TextField txtMaxBid;      // fx:id phải khớp trong FXML
    @FXML private TextField txtIncrement;   // fx:id phải khớp trong FXML

    private Auction currentAuction;
    private Timeline timeline;

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

    // Fix lỗi: Error resolving onAction='#handleNormalBid' (Nút Bid)
    @FXML
    private void handleNormalBid() {
        try {
            double amount = Double.parseDouble(txtNormalBid.getText());
            if (amount > currentAuction.getCurrentPrice()) {
                lblStatus.setText("Đã đặt thầu: " + amount);
                // TODO: Gửi yêu cầu bid lên Server ở đây
            } else {
                lblStatus.setText("Giá thầu phải cao hơn giá hiện tại!");
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Vui lòng nhập số tiền hợp lệ!");
        }
    }

    // QUAN TRỌNG: Fix lỗi LoadException trong ảnh image_28e735.png
    @FXML
    private void handleAutoBid() {
        try {
            double maxBid = Double.parseDouble(txtMaxBid.getText());
            double increment = Double.parseDouble(txtIncrement.getText());

            lblStatus.setText("Đã kích hoạt Auto-Bid: Max " + maxBid);
            // TODO: Gửi yêu cầu Auto-bid lên Server ở đây
        } catch (NumberFormatException e) {
            lblStatus.setText("Vui lòng nhập thông số Auto-bid hợp lệ!");
        }
    }

    @FXML
    private void handleExit() {
        if (timeline != null) timeline.stop(); // Dừng đồng hồ để tránh tốn tài nguyên
        lblId.getScene().getWindow().hide();
    }
}