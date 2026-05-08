package com.auction.client.controller.productcard;

import com.auction.client.controller.BiddingPopupController;
// 1. CHÚ Ý: Import đúng class Auction từ common.model!
import com.auction.common.model.Auction.Auction;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ProductCardController {
    @FXML private ImageView imgProduct;
    @FXML private Label lblProductName;
    @FXML private Label lblPrice;
    @FXML private Label lblTimer;
    @FXML private Button btnBid;

    // 2. Thêm dấu chấm phẩy
    private Auction auctionData;

    private int remainingSeconds;
    private Timeline timeline;

    public void setData(Auction auction, String imagePath) {
        this.auctionData = auction; // LƯU LẠI ĐỐI TƯỢNG ĐỂ DÙNG KHI BID

        // Lấy tên từ Item thay vì getItemName()
        if (lblProductName != null) lblProductName.setText(auction.getItemName());

        // Gọi getCurrentPrice() thay vì getCurrentHighestPrice()
        if (lblPrice != null) lblPrice.setText(auction.getCurrentHighestPrice() + " VNĐ");

        // Tạm thời fix cứng số giây hoặc bạn phải viết hàm tính khoảng cách từ (bây giờ) đến (endTime)
        this.remainingSeconds = 3600;
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidding_popup.fxml"));
            Parent root = loader.load();

            // Sửa lại theo đúng package chứa BiddingPopupController (nếu cần)
            BiddingPopupController popupController = loader.getController();
            popupController.initData(this.auctionData);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Chi tiết phiên đấu giá");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi mở Popup: " + e.getMessage());
        }
    }
    // 4. Đã xoá dấu ngoặc nhọn đóng class thừa ở đây

    // Hàm này bây giờ đã nằm gọn TRONG class
    private void startTimer() {
        if (lblTimer == null) return;

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

} // Đóng class thực sự ở đây