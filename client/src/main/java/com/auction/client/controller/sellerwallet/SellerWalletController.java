package com.auction.client.controller.sellerwallet;

import com.auction.common.model.User.Seller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class SellerWalletController {

    @FXML private Label lblBalance;
    private Seller seller;

    public void setSellerData(Seller seller) {
        this.seller = seller;
        if (this.seller != null) {
            updateBalanceDisplay();
        }
    }

    private void updateBalanceDisplay() {
        // Cập nhật nhãn hiển thị số dư từ đối tượng seller
        lblBalance.setText(String.format("%,.0f VNĐ", seller.getBalance()));
    }

    @FXML
    private void handleWithdraw() {
        TextInputDialog dialog = new TextInputDialog(); // Để trống mặc định để người dùng tự nhập
        dialog.setTitle("Rút tiền");
        dialog.setHeaderText("Số dư hiện tại: " + String.format("%,.0f VNĐ", seller.getBalance()));
        dialog.setContentText("Nhập số tiền muốn rút (VNĐ):");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(amountStr -> {
            try {
                // Kiểm tra nếu người dùng để trống
                if (amountStr.trim().isEmpty()) {
                    showError("Lỗi", "Vui lòng nhập số tiền!");
                    return;
                }

                double amount = Double.parseDouble(amountStr);

                // Kiểm tra số tiền âm hoặc bằng 0
                if (amount <= 0) {
                    showError("Lỗi", "Số tiền rút phải lớn hơn 0!");
                    return;
                }

                // Kiểm tra số dư
                if (amount > seller.getBalance()) {
                    showError("Giao dịch thất bại", "Số dư không đủ! Bạn chỉ có " + String.format("%,.0f VNĐ", seller.getBalance()));
                    return;
                }

                // Thực hiện trừ tiền và cập nhật
                double newBalance = seller.getBalance() - amount;
                seller.setBalance(newBalance);

                updateBalanceDisplay();
                showInfo("Thành công", "Bạn đã rút thành công " + String.format("%,.0f", amount) + " VNĐ.");

            } catch (NumberFormatException e) {
                // Xử lý khi người dùng nhập chữ thay vì số
                showError("Lỗi định dạng", "Vui lòng chỉ nhập con số hợp lệ!");
            }
        });
    }

    // Hàm hiển thị thông báo lỗi (Alert Error)
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Hàm hiển thị thông báo thành công (Alert Info)
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        if (lblBalance != null && lblBalance.getScene() != null) {
            Stage stage = (Stage) lblBalance.getScene().getWindow();
            stage.close();
        }
    }
}