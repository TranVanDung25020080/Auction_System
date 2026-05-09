package com.auction.client.controller.bidderdashboard;

import com.auction.client.controller.productcard.ProductCardController;
import com.auction.common.model.Auction.Auction; // Đảm bảo import bản client
import com.auction.client.service.http.GetAutionApi;
import com.auction.common.model.User.Bidder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import java.util.List;

public class BidderDashboardController {
    //FXML Fields:
    @FXML private FlowPane productContainer;
    @FXML private TextField bidderInfoTextField;

    //Other fields:
    private Bidder bidder;

    @FXML
    public void initialize() {
        renderProducts();
    }

    //Method for other classes to call:
    private void renderProducts() {
        productContainer.getChildren().clear();
        try {
            var response = new GetAutionApi().getAllAuction();
            // Sau khi đổi import, dòng này sẽ chạy mượt mà:
            List<Auction> auctionList = (List<Auction>) (Object) response.getAuctionList();

            for (Auction auction : auctionList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/product_card.fxml"));
                Parent card = loader.load();
                ProductCardController controller = loader.getController();

                // Bây giờ auction đã là bản common, card sẽ nhận được dữ liệu
                controller.setData(auction, "/com/auction/client/view/khanh.png");
                productContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log sẽ không còn ClassCastException nữa
        }
    }

    public void setBidder(int userId,String ownerName,String userName,double balance){
        this.bidder=new Bidder(userId,ownerName,userName,balance);
    }
    //Cai nay la phan hien thi thong tin bidder thoi khanh chua lam dep duoc nhe:)))
    public void setBidderInfoTextField(){
        String text="Id: "+bidder.getId()+"--Name: "+bidder.getOwnerName();
        this.bidderInfoTextField.setPrefWidth(Region.USE_COMPUTED_SIZE);
        this.bidderInfoTextField.setText(text);
    }

}