package com.auction.client.controller.bidderwallet.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.bidderwallet.BidderWalletController;
import com.auction.client.network.http.UserApi;
import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.model.User.Bidder;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Optional;

public class DepositButton {
    double currentBalance;
    Label lblBalance;

    public void handle(BidderWalletController bidderWalletController){
        Bidder bidder=bidderWalletController.getBidder();
        lblBalance=bidderWalletController.getLblBalance();
        currentBalance= bidderWalletController.getCurrentBalance();

        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Nạp tiền");
        dialog.setHeaderText("Nhập số tiền bạn muốn nạp:");
        dialog.setContentText("Số tiền (VNĐ):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    /*currentBalance += amount;*/
                    //Call api here:
                    int userId= bidder.getUserId();
                    UserBalanceRequestDTO userBalanceRequestDTO =new UserBalanceRequestDTO(userId,amount,currentBalance);

                    UserBalanceResponseDTO userBalanceResponseDTO =new UserApi().depositBalance(userBalanceRequestDTO);
                    this.currentBalance= userBalanceResponseDTO.getCurrentBalance();

                    // Cập nhật số dư vào đối tượng bidder
                    if (bidder != null) {
                        bidder.setBalance(currentBalance);
                    }

                    updateBalanceLabel();
                    Alert.showAlert("Thành công", "Bạn đã nạp " + String.format("%,.0f", amount) + " VNĐ.");
                }
                else{
                    com.auction.client.controller.annoucement.Alert.showAlert("ERROR","Your deposit amount must be strictly greater than zero!");
                }
            } catch (NumberFormatException e) {
                com.auction.client.controller.annoucement.Alert.showAlert("Lỗi", "Vui lòng nhập số tiền hợp lệ!");
            } catch (IOException e) {
                Alert.showAlert("EROR",e.getMessage());
            }
        });

    }

    private void updateBalanceLabel() {
        lblBalance.setText(String.format("%,.0f VNĐ", currentBalance));
    }
}
