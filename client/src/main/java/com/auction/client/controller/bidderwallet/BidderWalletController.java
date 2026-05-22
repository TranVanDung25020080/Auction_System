package com.auction.client.controller.bidderwallet;

import com.auction.client.controller.bidderwallet.buttonhandler.CloseButton;
import com.auction.client.controller.bidderwallet.buttonhandler.DepositButton;
import com.auction.common.model.User.Bidder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.Optional;

public class BidderWalletController {
    //FXML fields:
    @FXML private Label lblBalance;
    @FXML private Button depositButton,closeButton;
    //other fields:
    private Bidder bidder;
    private double currentBalance;

    //
    public void initialize(){
        // set on action for buttons:
        this.depositButton.setOnAction(event -> new DepositButton().handle(this));

        this.closeButton.setOnAction(event -> new CloseButton().handle(this));
    }


    // Method for other classes to call
    public void setBidderData(Bidder bidder) {
        this.bidder = bidder;
        this.currentBalance = bidder.getBalance();
        updateBalanceLabel();
    }

    public void updateBalanceLabel() {
        lblBalance.setText(String.format("%,.0f VNĐ", currentBalance));
    }

    public Bidder getBidder(){
        return this.bidder;
    }
    public double getCurrentBalance(){
        return this.currentBalance;
    }
    public Label getLblBalance(){
        return this.lblBalance;
    }
}