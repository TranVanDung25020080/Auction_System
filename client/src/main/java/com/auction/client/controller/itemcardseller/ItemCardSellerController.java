package com.auction.client.controller.itemcardseller;


import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.itemcardseller.buttonhandler.RemoveItemButton;
import com.auction.client.controller.itemcardseller.buttonhandler.OpenAuctionButton;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ItemCardSellerController {
    @FXML private VBox cardContainer;
    @FXML private Label lblBadge;
    @FXML private Label lblItemName;
    @FXML private Label lblPrice;
    @FXML private Label lblExtraInfo;
    @FXML private Button openAuctionButton, removeItemButton;

    private int sellerId;
    private Item item;

    @FXML
    public void initialize() {
        //Set on action for buttons:
        this.openAuctionButton.setOnAction(event -> {
            try {
                new OpenAuctionButton().handle(this, new RemoveItemButton());
            } catch (IOException e) {
                Alert.showAlert("ERROR", e.getMessage());
            }
        });

        this.removeItemButton.setOnAction(event -> new RemoveItemButton().handle(this));
    }

    //Method for other classes to call:
    public void setItemData(Item item, int sellerId) {
        this.sellerId = sellerId;
        this.item = item;

        lblItemName.setText(item.getName());
        lblPrice.setText(String.format("%,.0f VNĐ", item.getInitialPrice()));

        // Reset style badge để tránh lỗi hiển thị khi tái sử dụng card
        lblBadge.getStyleClass().removeAll("badge-art", "badge-electronics", "badge-vehicle");

        if (item instanceof Art) {
            Art art = (Art) item;
            lblBadge.setText("NGHỆ THUẬT");
            lblBadge.getStyleClass().add("badge-art");
            lblExtraInfo.setText("Tác giả: " + art.getAuthor());
        }
        else if (item instanceof Electronics) {
            Electronics elec = (Electronics) item;
            lblBadge.setText("ĐIỆN TỬ");
            lblBadge.getStyleClass().add("badge-electronics");
            lblExtraInfo.setText("Bảo hành: " + elec.getWarranty() + " tháng");
        }
        else if (item instanceof Vehicle) {
            Vehicle vehi = (Vehicle) item;
            lblBadge.setText("PHƯƠNG TIỆN");
            lblBadge.getStyleClass().add("badge-vehicle");
            lblExtraInfo.setText("Hãng xe: " + vehi.getCompany());
        }
    }

    public VBox getCardContainer() { return cardContainer; }
    public Item getItem() { return item; }
    public int getSellerId() { return sellerId; }
}