package com.auction.client.controller.additem.buttonhandler;

import com.auction.client.controller.additem.AddItemController;
import com.auction.client.controller.annoucement.Alert;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXML;

public class SaveButton {
    @FXML
    public void handle(AddItemController addItemController, CloseButton closeButton) {
        try {
            String type = addItemController.getCbItemType().getValue();
            if (type == null) {
                Alert.showAlert("Lỗi", "Vui lòng chọn loại mặt hàng!");
                return;
            }

            String name = addItemController.getTxtName().getText();
            String desc = addItemController.getTxtDescription().getText();
            double price = Double.parseDouble(addItemController.getTxtPrice().getText());

            Item newItem = null;

            if (type.contains("Art")) {
                newItem = new Art(0, name, desc, price, null, ItemStatus.AVAILABLE, addItemController.getTxtExtra().getText());
            } else if (type.contains("Electronics")) {
                int warranty = Integer.parseInt(addItemController.getTxtExtra().getText());
                newItem = new Electronics(0, name, desc, price, null, ItemStatus.AVAILABLE, warranty);
            } else if (type.contains("Vehicle")) {
                newItem = new Vehicle(0, name, desc, price, null, ItemStatus.AVAILABLE, addItemController.getTxtExtra().getText());
            }

            System.out.println("Đã tạo thành công: " + newItem.getName());
            closeButton.handle(addItemController);

        } catch (NumberFormatException e) {
            Alert.showAlert("Lỗi định dạng", "Giá tiền hoặc thông số kỹ thuật phải là số!");
        } catch (Exception e) {
            Alert.showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin!");
            e.printStackTrace();
        }
    }
}
