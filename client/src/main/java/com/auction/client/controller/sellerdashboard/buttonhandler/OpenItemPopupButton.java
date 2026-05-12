package com.auction.client.controller.sellerdashboard.buttonhandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenItemPopupButton {
    public void handle(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/add_item_popup.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Thêm sản phẩm mới");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            System.err.println("Lỗi mở popup: " + e.getMessage());
        }

    }
}
