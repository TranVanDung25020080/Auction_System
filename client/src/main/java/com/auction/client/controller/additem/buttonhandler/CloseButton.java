package com.auction.client.controller.additem.buttonhandler;

import com.auction.client.controller.additem.AddItemController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CloseButton {
    @FXML
    public void handle(AddItemController addItemController) {
        Stage stage = (Stage) addItemController.getTxtName().getScene().getWindow();
        stage.close();
    }
}
