package com.auction.client.controller; // Kiểm tra lại package này!

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.util.Duration;

public class ProductCardController {
    @FXML private ImageView imgProduct;
    @FXML private Label lblProductName;
    @FXML private Label lblPrice;
    @FXML private Label lblTimer;
    @FXML private Button btnBid;

    private int remainingSeconds;
    private Timeline timeline;

    public void setData(String name, String price, String imagePath, int durationSeconds) {
        // Kiểm tra an toàn trước khi set text để tránh NullPointerException
        if (lblProductName != null) lblProductName.setText(name);
        if (lblPrice != null) lblPrice.setText(price + " VNĐ");

        this.remainingSeconds = durationSeconds;

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

    @FXML
    private void handleBid() {
        if (lblProductName != null) {
            System.out.println("Đấu giá cho: " + lblProductName.getText());
        }
    }

    private void startTimer() {
        if (lblTimer == null) return; // Nếu FXML chưa đặt fx:id cho timer thì thoát

        if (timeline != null) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingSeconds--;
            if (remainingSeconds <= 0) {
                lblTimer.setText("Hết giờ");
                if (btnBid != null) btnBid.setDisable(true);
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
}