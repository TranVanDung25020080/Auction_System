package com.auction.client.controller;

import com.auction.common.model.User.Bidder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.Optional;

public class BidderWalletController {
    @FXML private Label lblBalance;
    private Bidder bidder;
    private double currentBalance;

    // Hàm nhận dữ liệu từ Dashboard truyền sang
    public void setBidderData(Bidder bidder) {
        this.bidder = bidder;
        this.currentBalance = bidder.getBalance();
        updateBalanceLabel();
    }

    private void updateBalanceLabel() {
        lblBalance.setText(String.format("%,.0f VNĐ", currentBalance));
    }

    @FXML
    private void handleDeposit() {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Nạp tiền");
        dialog.setHeaderText("Nhập số tiền bạn muốn nạp:");
        dialog.setContentText("Số tiền (VNĐ):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    currentBalance += amount;
                    // Cập nhật số dư vào đối tượng bidder
                    if (this.bidder != null) {
                        this.bidder.setBalance(currentBalance);
                    }
                    updateBalanceLabel();
                    showInfo("Thành công", "Bạn đã nạp " + String.format("%,.0f", amount) + " VNĐ.");
                }
            } catch (NumberFormatException e) {
                showError("Lỗi", "Vui lòng nhập số tiền hợp lệ!");
            }
        });
    }

    @FXML
    private void handleClose() {
        // Lấy Stage và đóng cửa sổ
        Stage stage = (Stage) lblBalance.getScene().getWindow();
        stage.close();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}