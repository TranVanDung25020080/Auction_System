package com.auction.client.controller.bidderdashboard;

import com.auction.client.controller.bidderdashboard.buttonhandler.ReloadButton;
import com.auction.client.controller.bidderwallet.BidderWalletController;
import com.auction.client.controller.productcard.ProductCardController;
import com.auction.common.model.Auction.Auction; // Đảm bảo import bản client
import com.auction.client.network.http.AuctionApi;
import com.auction.common.model.User.Bidder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BidderDashboardController {
    //FXML Fields:
    @FXML private FlowPane productContainer;
    @FXML private TextField bidderInfoTextField;
    @FXML private Button reloadButton;

    //Other fields:
    private Bidder bidder;
    //
    public void initialize(){
        this.reloadButton.setOnAction(event -> new ReloadButton().handle(this));
    }

    //Method for other classes to call:
    public void renderProducts() {
        productContainer.getChildren().clear();
        try {
            var response = new AuctionApi().getAllAuction();
            // Sau khi đổi import, dòng này sẽ chạy mượt mà:
            List<Auction> auctionList = (List<Auction>) (Object) response.getAuctionList();

            for (Auction auction : auctionList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/product_card.fxml"));
                Parent card = loader.load();
                ProductCardController controller = loader.getController();


                // Bây giờ auction đã là bản common, card sẽ nhận được dữ liệu
                controller.setBidder(this.bidder);
                controller.setData(auction, "/com/auction/client/view/khanh.png", bidder.getUserId());
                productContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log sẽ không còn ClassCastException nữa
        }
    }

    public void setBidder(int userId,String ownerName,String userName,double balance){
        this.bidder=new Bidder(userId,ownerName,userName,balance);

        renderProducts();
    }
    //Cai nay la phan hien thi thong tin bidder thoi khanh chua lam dep duoc nhe:)))
    public void setBidderInfoTextField(){
        String text="Id: "+bidder.getId()+"--Name: "+bidder.getOwnerName();
        this.bidderInfoTextField.setPrefWidth(Region.USE_COMPUTED_SIZE);
        this.bidderInfoTextField.setText(text);
    }
    public Bidder getBidder(){
        return this.bidder;
    }


    // wallet nhé
    @FXML
    private void handleShowWallet() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidder_wallet.fxml"));
            Parent root = loader.load();

            // 1. Lấy controller của ví và truyền dữ liệu bidder hiện tại sang
            BidderWalletController walletController = loader.getController();
            walletController.setBidderData(this.bidder);

            Stage walletStage = new Stage();
            walletStage.setTitle("Ví tiền của tôi");
            walletStage.initModality(Modality.APPLICATION_MODAL);
            walletStage.setScene(new Scene(root));
            walletStage.setResizable(false);

            // 2. Đợi cho đến khi cửa sổ ví đóng lại
            walletStage.showAndWait();

            // 3. Sau khi đóng ví, cập nhật lại cái TextField hiển thị thông tin ở Dashboard
            setBidderInfoTextField();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}