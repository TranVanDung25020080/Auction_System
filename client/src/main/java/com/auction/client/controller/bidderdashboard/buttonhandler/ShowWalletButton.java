package com.auction.client.controller.bidderdashboard.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.bidderdashboard.BidderDashboardController;
import com.auction.client.controller.bidderwallet.BidderWalletController;
import com.auction.common.model.User.Bidder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowWalletButton {
    public void handle(BidderDashboardController bidderDashboardController) {
        Bidder bidder=bidderDashboardController.getBidder();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidder_wallet.fxml"));
            Parent root = loader.load();

            // 1. Lấy controller của ví và truyền dữ liệu bidder hiện tại sang
            BidderWalletController walletController = loader.getController();
            walletController.setBidderData(bidder);

            Stage walletStage = new Stage();
            walletStage.setTitle("Ví tiền của tôi");
            walletStage.initModality(Modality.APPLICATION_MODAL);
            walletStage.setScene(new Scene(root));
            walletStage.setResizable(false);

            // 2. Đợi cho đến khi cửa sổ ví đóng lại
            walletStage.showAndWait();

            // 3. Sau khi đóng ví, cập nhật lại cái TextField hiển thị thông tin ở Dashboard
            bidderDashboardController.setBidderInfoTextField();

        } catch (IOException e) {
            Alert.showAlert("ERROR",e.getMessage());
            e.printStackTrace();
        }

    }

}
