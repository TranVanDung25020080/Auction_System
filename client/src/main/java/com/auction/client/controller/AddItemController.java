package com.auction.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController {

    @FXML private TextField txtItemName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtImageUrl;

    // Xử lý khi bấm nút "Lưu Lại"
    @FXML
    private void handleSave() {
        String name = txtItemName.getText();
        String price = txtPrice.getText();
        String imageUrl = txtImageUrl.getText();

        // Kiểm tra xem người dùng có nhập thiếu không
        if (name.isEmpty() || price.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập tên và giá khởi điểm!");
            return;
        }

        // TODO: Sau này kết nối Database thì sẽ tạo Object Item và lưu xuống DB ở đây
        System.out.println("ĐÃ THÊM: " + name + " | Giá: " + price + " | Ảnh: " + imageUrl);

        // Thêm thành công thì đóng cửa sổ
        closeWindow();
    }

    // Xử lý khi bấm nút "Hủy"
    @FXML
    private void handleCancel() {
        closeWindow();
    }

    // Hàm hỗ trợ tắt cửa sổ (Stage) hiện tại
    private void closeWindow() {
        Stage stage = (Stage) txtItemName.getScene().getWindow();
        stage.close();
    }

    // Hàm hiện thông báo lỗi nhanh
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}