package com.auction.client.controller.openauctionpopup;

import com.auction.client.controller.additem.buttonhandler.CloseButton;
import com.auction.client.controller.additem.buttonhandler.SaveButton;
import com.auction.client.controller.openauctionpopup.buttonhandler.CancelButton;
import com.auction.client.controller.openauctionpopup.buttonhandler.ConfirmButton;
import com.auction.common.model.Item.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OpenAuctionPopupController {
    //FXML fields:
    @FXML private Label lblItemName;
    @FXML private TextField txtStartPrice; // Thêm bid step ở đây
    @FXML private DatePicker dpEndDate;
    @FXML private TextField  txtEndTime;
    @FXML private Button confirmButton, cancelButton;
    //Other fields:
    private Item item;
    private int sellerId;
    private boolean confirmed = false;

    @FXML
    public void initialize() {
        dpEndDate.setValue(LocalDate.now().plusDays(1));

        this.confirmButton.setOnAction(event -> new ConfirmButton().handle(this));

        this.cancelButton.setOnAction(event -> new CancelButton().handle(this));
    }

    public void setItemData(Item item,int sellerId) {
        this.item = item;
        this.sellerId=sellerId;

        lblItemName.setText(item.getName());
        txtStartPrice.setText(String.valueOf(item.getInitialPrice()));
    }

    public boolean isConfirmed() { return confirmed; }
    public Label getLblItemName() { return lblItemName; }
    public TextField getTxtStartPrice() { return txtStartPrice; }
    public DatePicker getDpEndDate() { return dpEndDate; }
    public TextField getTxtEndTime() { return txtEndTime; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

}