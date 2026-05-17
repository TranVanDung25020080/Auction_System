package com.auction.client.controller.itemcardseller.buttonhandler;

import com.auction.client.controller.itemcardseller.ItemCardSellerController;
import com.auction.common.model.Item.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;

import java.util.Optional;

public class RemoveCardButton {
    @FXML
    public void handle(ItemCardSellerController itemCardSellerController) {
        Item item = itemCardSellerController.getItem();
        if (item == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText("Bạn có chắc chắn muốn xóa sản phẩm này khỏi kho?");
        confirm.setContentText(item.getName());

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            removeCardFromUI(itemCardSellerController);

            // TODO: Gửi lệnh xóa (id) lên Server qua Socket
            System.out.println("Đã gửi yêu cầu xóa sản phẩm ID: " + item.getId());
        }
    }

    public void removeCardFromUI(ItemCardSellerController itemCardSellerController) {
        FlowPane parent = (FlowPane) itemCardSellerController.getCardContainer().getParent();
        if (parent != null) {
            parent.getChildren().remove(itemCardSellerController.getCardContainer());
        }
    }
}
