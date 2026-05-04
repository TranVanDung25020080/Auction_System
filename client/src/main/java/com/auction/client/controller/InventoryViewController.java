package com.auction.client.controller;

import com.auction.client.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryViewController {
    @FXML private FlowPane inventoryContainer;

    @FXML
    public void initialize() {
        loadInventoryItems();
    }

    private void loadInventoryItems() {
        inventoryContainer.getChildren().clear();
        List<Item> myItems = new ArrayList<>();
        myItems.add(new Item("1", "Đồng hồ Rolex Cổ", "/com/auction/client/images/khanh.png", "Trong kho", 15000000));
        myItems.add(new Item("2", "Tranh Phong Cảnh", "/com/auction/client/images/khanh.png", "Trong kho", 5000000));
        myItems.add(new Item("3", "Bình Gốm Sứ", "/com/auction/client/images/khanh.png", "Trong kho", 2500000));

        try {
            for (Item item : myItems) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/inventory_item_card.fxml"));
                Parent card = loader.load();
                InventoryCardController controller = loader.getController();
                controller.setItemData(item);
                inventoryContainer.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}