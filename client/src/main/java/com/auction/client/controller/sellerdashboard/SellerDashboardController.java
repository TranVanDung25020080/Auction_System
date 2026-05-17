package com.auction.client.controller.sellerdashboard;

import com.auction.client.controller.sellerdashboard.buttonhandler.OpenItemPopupButton;
import com.auction.client.controller.sellerdashboard.buttonhandler.ShowIntentoryButton;
import com.auction.client.controller.sellerdashboard.buttonhandler.ShowLiveAuctionsButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;


public class SellerDashboardController {

    //FXML Fields:
    @FXML private FlowPane flowPaneContent;
    @FXML private Label lblHeader;
    @FXML private Button openItemPopupButton,showInventoryButton,showLiveAuctionsButton;
    //Other fields:
    private int sellerId;


    //Method which is gonna be called automaticly:
    public void initialize() {

        //Set on action for buttons:
        this.openItemPopupButton.setOnAction(event -> new OpenItemPopupButton().handle(this));

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
    public int getSellerId(){
        return this.sellerId;
    }
    public void setSellerId(int sellerId){
        this.sellerId=sellerId;
    }
}