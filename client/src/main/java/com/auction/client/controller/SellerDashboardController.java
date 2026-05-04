package com.auction.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class SellerDashboardController {

    @FXML
    private BorderPane mainRoot;

    @FXML
    public void initialize() {
        // Khi vừa mở dashboard, tự động hiện Kho vật phẩm luôn
        loadView("/com/auction/client/view/inventory_view.fxml");
    }

    // Hàm dùng chung để chuyển đổi giao diện ở vùng giữa
    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainRoot.setCenter(view);
        } catch (IOException e) {
            System.err.println("Không tìm thấy file FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowInventory(ActionEvent event) {
        loadView("/com/auction/client/view/inventory_view.fxml");
    }

    @FXML
    private void handleShowAuctions(ActionEvent event) {
        loadView("/com/auction/client/view/my_auctions.fxml");
    }

    @FXML
    private void handleAddItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/add_item_form.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.setTitle("Thêm Vật Phẩm Mới");
            popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            popupStage.setScene(new javafx.scene.Scene(root));
            popupStage.setResizable(false);
            popupStage.showAndWait();

            // Sau khi đóng popup thêm món đồ, ta có thể load lại Kho để cập nhật
            loadView("/com/auction/client/view/inventory_view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}