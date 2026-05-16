package com.auction.client.controller.additem.buttonhandler;

import com.auction.client.controller.additem.AddItemController;
import com.auction.client.controller.annoucement.Alert;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TypeChangeButton {
    @FXML
    public void handle(AddItemController addItemController) {

        addItemController.getVboxDynamicFields().getChildren().clear();
        String type = addItemController.getCbItemType().getValue();
        if (type == null) return;

        Label lbl = new Label();
        TextField txtExtra = new TextField();

        // Gán style từ CSS để đồng bộ giao diện
        txtExtra.getStyleClass().add("form-input");

        if (type.contains("Art")) {
            lbl.setText("Tên tác giả:");
            txtExtra.setPromptText("Ví dụ: Leonardo da Vinci");
        } else if (type.contains("Electronics")) {
            lbl.setText("Tháng bảo hành:");
            txtExtra.setPromptText("Ví dụ: 12");
        } else if (type.contains("Vehicle")) {
            lbl.setText("Hãng sản xuất:");
            txtExtra.setPromptText("Ví dụ: Toyota, Mercedes...");
        }
        addItemController.setTxtExtra(txtExtra);

        addItemController.getVboxDynamicFields().getChildren().addAll(lbl, txtExtra);
    }

}
