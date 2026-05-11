package com.auction.client.controller;

import com.auction.common.model.Auction.Auction;
import com.auction.common.enums.AuctionStatus; // NHỚ IMPORT CÁI NÀY
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;

public class AuctionCardSellerController {
    @FXML private Label lblItemName, lblCurrentPrice, lblTimeLeft, lblWinnerInfo, lblAuctionId;

    private Timeline timeline;
    private Auction auction;

    public void setAuctionData(Auction auction) {
        this.auction = auction;

        lblAuctionId.setText("#AUC-" + auction.getAuctionId());
        lblItemName.setText(auction.getItemName());

        updateDisplay();

        // CHỈ CHẠY ĐẾM NGƯỢC NẾU TRẠNG THÁI LÀ OPEN (Khớp với Enum bạn gửi)
        if (auction.getStatus() == AuctionStatus.OPEN) {
            startCountdown();
        } else if (auction.getStatus() == AuctionStatus.FINISHED) {
            lblTimeLeft.setText("ĐÃ KẾT THÚC");
        } else {
            lblTimeLeft.setText("CHỜ BẮT ĐẦU");
        }
    }

    public void updateDisplay() {
        lblCurrentPrice.setText(String.format("%,.0f VNĐ", auction.getCurrentHighestPrice()));

        int winnerId = auction.getWinningBidderId();
        if (winnerId > 0) {
            lblWinnerInfo.setText("Người dẫn đầu: ID #" + winnerId);
            lblWinnerInfo.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        } else {
            lblWinnerInfo.setText("Chưa có người đặt giá");
            lblWinnerInfo.setStyle("-fx-text-fill: #95a5a6;");
        }
    }

    private void startCountdown() {
        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            java.time.Duration d = java.time.Duration.between(LocalDateTime.now(), auction.getEndTime());

            if (d.isNegative() || d.isZero()) {
                lblTimeLeft.setText("HẾT GIỜ");
                lblTimeLeft.getStyleClass().add("timer-urgent"); // Chuyển sang màu đỏ
                timeline.stop();
            } else {
                long hours = d.toHours();
                long minutes = d.toMinutesPart();
                long seconds = d.toSecondsPart();

                lblTimeLeft.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                // Hiệu ứng cảnh báo đỏ khi dưới 1 phút
                if (d.toMinutes() < 1) {
                    if (!lblTimeLeft.getStyleClass().contains("timer-urgent")) {
                        lblTimeLeft.getStyleClass().add("timer-urgent");
                    }
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void handleViewDetails() {
        try {
            // 1. Load file FXML vừa tạo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/auction_history_popup.fxml"));
            Parent root = loader.load();

            // 2. Tạo Stage mới (Cửa sổ mới)
            Stage stage = new Stage();
            stage.setTitle("Lịch sử đấu giá - " + auction.getItemName());

            // Làm cho cửa sổ này ở trên cùng, phải đóng nó mới bấm được Dashboard
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Đã mở cửa sổ lịch sử cho: " + auction.getItemName());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể mở cửa sổ lịch sử!");
        }
    }

    // Hàm bổ trợ để Dashboard có thể dừng timeline khi cần
    public void stopTimer() {
        if (timeline != null) timeline.stop();
    }
}