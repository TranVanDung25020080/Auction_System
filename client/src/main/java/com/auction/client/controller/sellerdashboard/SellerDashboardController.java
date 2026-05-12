package com.auction.client.controller.sellerdashboard;

import com.auction.client.controller.auctioncardseller.AuctionCardSellerController;
import com.auction.client.controller.itemcardseller.ItemCardSellerController;
import com.auction.client.controller.sellerdashboard.buttonhandler.OpenItemPopupButton;
import com.auction.client.controller.sellerdashboard.buttonhandler.ShowIntentoryButton;
import com.auction.client.controller.sellerdashboard.buttonhandler.ShowLiveAuctionsButton;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SellerDashboardController {

    @FXML private FlowPane flowPaneContent;
    @FXML private Label lblHeader;
    @FXML private Button openItemPopupButton,showInventoryButton,showLiveAuctionsButton;

    @FXML
    public void initialize() {
       /* // Tự động load kho hàng khi vừa vào dashboard
        showInventory();*/

        //Set on action for buttons:
        this.openItemPopupButton.setOnAction(event -> new OpenItemPopupButton().handle());

        this.showInventoryButton.setOnAction(event ->
                new ShowIntentoryButton().handle(this));

        this.showLiveAuctionsButton.setOnAction(event -> new ShowLiveAuctionsButton().handle(this));



    }

    //Method for other classes to call
    public FlowPane getFlowPaneContent(){
        return this.flowPaneContent;
    }

    public Label getLblHeader() {
        return this.lblHeader;
    }
}