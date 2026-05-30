package com.auction.client.controller.bidderminiwallet;

import com.auction.client.controller.bidderminiwallet.buttonhandler.CancelButton;
import com.auction.client.controller.bidderminiwallet.buttonhandler.OkButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BidderMiniWalletController {
    //FXML fields:
    @FXML private TextField maxBidField;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    //Other fields:
    private double currentBalance ;
    private double chosenMaxBid ;

    public void initialize() {
        // Set on action for buttons:
        okButton.setOnAction(event -> new OkButton().handle(this));
        cancelButton.setOnAction(event -> new CancelButton().handle(this));
    }

    //Method for other classes to call:
    public void setData(double balance) {
        this.currentBalance = balance;
        this.maxBidField.clear();
        this.chosenMaxBid = -1.0;
    }


    // --- GETTER / SETTER ---

    public TextField getMaxBidField() {
        return maxBidField;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getChosenMaxBid() {
        return chosenMaxBid;
    }

    public void setChosenMaxBid(double chosenMaxBid) {
        this.chosenMaxBid = chosenMaxBid;
    }
}