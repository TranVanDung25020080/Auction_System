package com.auction.client.controller;

import com.auction.common.model.Item.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OpenAuctionPopupController {

    @FXML private Label lblItemName;
    @FXML private TextField txtStartPrice, txtBidStep; // Thêm bid step ở đây
    @FXML private DatePicker dpStartDate, dpEndDate;
    @FXML private TextField txtStartTime, txtEndTime;

    private Item item;
    private boolean confirmed = false;

    @FXML
    public void initialize() {
        dpStartDate.setValue(LocalDate.now());
        dpEndDate.setValue(LocalDate.now().plusDays(1));
    }

    public void setItemData(Item item) {
        this.item = item;
        lblItemName.setText(item.getName());
        txtStartPrice.setText(String.valueOf(item.getInitialPrice()));
    }

    @FXML
    private void handleConfirm() {
        try {
            // Lấy dữ liệu giá và bước giá
            double startPrice = Double.parseDouble(txtStartPrice.getText());
            double bidStep = Double.parseDouble(txtBidStep.getText());

            // Lấy dữ liệu thời gian
            LocalDateTime start = LocalDateTime.of(dpStartDate.getValue(), LocalTime.parse(txtStartTime.getText()));
            LocalDateTime end = LocalDateTime.of(dpEndDate.getValue(), LocalTime.parse(txtEndTime.getText()));

            if (end.isBefore(start)) {
                showAlert("Lỗi", "Thời gian kết thúc phải sau khi bắt đầu!");
                return;
            }

            // In ra log để check
            System.out.println("--- THÔNG TIN PHIÊN ĐẤU GIÁ MỚI ---");
            System.out.println("Sản phẩm: " + item.getName());
            System.out.println("Giá sàn: " + startPrice + " | Bước giá: " + bidStep);
            System.out.println("Thời gian: " + start + " đến " + end);

            confirmed = true;
            closeStage();

        } catch (Exception e) {
            showAlert("Lỗi nhập liệu", "Vui lòng kiểm tra lại định dạng số và thời gian (HH:mm)!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML private void handleCancel() { confirmed = false; closeStage(); }
    private void closeStage() { ((Stage) lblItemName.getScene().getWindow()).close(); }
    public boolean isConfirmed() { return confirmed; }
}