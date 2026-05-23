package com.auction.client.controller.sellerdashboard;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.sellerdashboard.buttonhandler.OpenItemPopupButton;
import com.auction.client.controller.sellerdashboard.buttonhandler.ShowIntentoryButton;
import com.auction.client.controller.sellerdashboard.buttonhandler.ShowLiveAuctionsButton;
import com.auction.client.controller.sellerwallet.SellerWalletController;
import com.auction.common.model.User.Seller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class SellerDashboardController {

    //FXML Fields:
    @FXML private FlowPane flowPaneContent;
    @FXML private Label lblHeader;
    @FXML private Button openItemPopupButton,showInventoryButton,showLiveAuctionsButton;
    //Other fields:
    private int sellerId;
    private Seller seller;

    public void setSellerData(Seller seller) {
        this.seller = seller;
        this.sellerId = seller.getUserId();
    }


    //Method which is gonna be called automaticly:
    public void initialize() {

        //Set on action for buttons:
        this.openItemPopupButton.setOnAction(event -> new OpenItemPopupButton().handle(this));

        this.showInventoryButton.setOnAction(event ->
                new ShowIntentoryButton().handle(this));

        this.showLiveAuctionsButton.setOnAction(event -> {
            try {
                new ShowLiveAuctionsButton().handle(this);
            } catch (IOException e) {
                Alert.showAlert("ERROR",e.getMessage());
            }
        });



    }

    //Method for other classes to call
    public FlowPane getFlowPaneContent(){
        return this.flowPaneContent;
    }
    public Label getLblHeader() {
        return this.lblHeader;
    }
    public int getSellerId(){
        return this.sellerId;
    }
    public void setSellerId(int sellerId){
        this.sellerId=sellerId;
    }


    //wallet
    @FXML
    private void handleOpenWallet() {
        try {
            // 1. Load file FXML của ví Seller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/seller_wallet.fxml"));
            Parent root = loader.load();

            // 2. Lấy controller của ví và truyền dữ liệu Seller hiện tại sang
            SellerWalletController walletController = loader.getController();
            walletController.setSellerData(this.seller); // Giả sử biến 'seller' là thông tin người đang đăng nhập

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