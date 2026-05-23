package com.auction.client.controller.itemcardseller.buttonhandler;

import com.auction.client.controller.itemcardseller.ItemCardSellerController;
import com.auction.client.network.http.ItemAPI;
import com.auction.common.dto.request.ItemRequestDTO;
import com.auction.common.dto.response.ItemResponseDTO;
import com.auction.common.model.Item.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Optional;

public class RemoveItemButton {
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
            int itemId=item.getId();
            ItemRequestDTO itemRequestDTO=new ItemRequestDTO(itemId);

            try{
                ItemResponseDTO itemResponseDTO=new ItemAPI().removeItem(itemRequestDTO);

                com.auction.client.controller.annoucement.Alert.showAlert("ANNOUNCEMENT",itemResponseDTO.getMessage());
            } catch (IOException e) {
                com.auction.client.controller.annoucement.Alert.showAlert("ERROR",e.getMessage());
            }
        }
    }

    public void removeCardFromUI(ItemCardSellerController itemCardSellerController) {
        FlowPane parent = (FlowPane) itemCardSellerController.getCardContainer().getParent();
        if (parent != null) {
            parent.getChildren().remove(itemCardSellerController.getCardContainer());
        }
    }
}
