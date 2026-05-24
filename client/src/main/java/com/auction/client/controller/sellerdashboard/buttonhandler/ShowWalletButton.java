package com.auction.client.controller.sellerdashboard.buttonhandler;

import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.client.controller.sellerwallet.SellerWalletController;
import com.auction.common.model.User.Seller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowWalletButton {
    public void handle(SellerDashboardController sellerDashboardController){

        Seller seller=sellerDashboardController.getSeller();

        try {
            // 1. Load file FXML của ví Seller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/seller_wallet.fxml"));
            Parent root = loader.load();

            // 2. Lấy controller của ví và truyền dữ liệu Seller hiện tại sang
            SellerWalletController walletController = loader.getController();
            walletController.setSellerData(seller); // Giả sử biến 'seller' là thông tin người đang đăng nhập

            // 3. Hiển thị cửa sổ mới (Popup)
            Stage stage = new Stage();
            stage.setTitle("Ví Người Bán");
            stage.initModality(Modality.APPLICATION_MODAL); // Bắt buộc xử lý xong ví mới quay lại Dashboard được
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Không tìm thấy file seller_wallet.fxml! Kiểm tra lại đường dẫn.");
            e.printStackTrace();
        }

    }

}
