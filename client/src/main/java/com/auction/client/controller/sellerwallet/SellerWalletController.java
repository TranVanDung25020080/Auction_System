package com.auction.client.controller.sellerwallet;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.network.http.UserApi;
import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.model.User.Seller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
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
                    Alert.showAlert("Lỗi", "Vui lòng nhập số tiền!");
                    return;
                }

                double amount = Double.parseDouble(amountStr);

                // Kiểm tra số tiền âm hoặc bằng 0
                if (amount <= 0) {
                    Alert.showAlert("Lỗi", "Số tiền rút phải lớn hơn 0!");
                    return;
                }

                // Kiểm tra số dư
                if (amount > seller.getBalance()) {
                    Alert.showAlert("Giao dịch thất bại", "Số dư không đủ! Bạn chỉ có " + String.format("%,.0f VNĐ", seller.getBalance()));
                    return;
                }

                // Thực hiện trừ tiền và cập nhật and call api here:
                int userId=this.seller.getUserId();
                double balance=this.seller.getBalance();

                UserBalanceRequestDTO userBalanceRequestDTO=new UserBalanceRequestDTO(userId,amount,balance);

                UserBalanceResponseDTO userBalanceResponseDTO=new UserApi().withdraw(userBalanceRequestDTO);


                double newBalance = userBalanceResponseDTO.getCurrentBalance();
                seller.setBalance(newBalance);

                //
                updateBalanceDisplay();
                com.auction.client.controller.annoucement.Alert.showAlert("Thành công", "Bạn đã rút thành công " + String.format("%,.0f", amount) + " VNĐ.");

            } catch (NumberFormatException e) {
                // Xử lý khi người dùng nhập chữ thay vì số
                Alert.showAlert("Lỗi định dạng", "Vui lòng chỉ nhập con số hợp lệ!");
            } catch (IOException e) {
                Alert.showAlert("ERROR",e.getMessage());
            }
        });
    }

  /*  // Hàm hiển thị thông báo lỗi (Alert Error)
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }*/

   /* // Hàm hiển thị thông báo thành công (Alert Info)
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }*/

    @FXML
    private void handleClose() {
        if (lblBalance != null && lblBalance.getScene() != null) {
            Stage stage = (Stage) lblBalance.getScene().getWindow();
            stage.close();
        }
    }
}