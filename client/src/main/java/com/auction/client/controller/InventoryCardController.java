package com.auction.client.controller;


import com.auction.client.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryCardController {

    @FXML private ImageView imgItem;
    @FXML private Label lblItemName;
    @FXML private Label lblStatus;
    @FXML private Button btnAuction;

    private Item currentItem;

    public void setItemData(Item item) {
        this.currentItem = item;
        lblItemName.setText(item.getName());
        lblStatus.setText("Trạng thái: " + item.getStatus());

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            try {
                String path = getClass().getResource(item.getImagePath()).toExternalForm();
                imgItem.setImage(new Image(path));
            } catch (Exception e) {
                System.err.println("Không tìm thấy ảnh: " + item.getImagePath());
            }
        }

        btnAuction.setOnAction(event -> {
            System.out.println("Chuẩn bị đưa lên sàn: " + currentItem.getName());
        });
    }
}
