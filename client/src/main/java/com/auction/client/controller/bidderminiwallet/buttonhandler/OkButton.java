package com.auction.client.controller.bidderminiwallet.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.bidderminiwallet.BidderMiniWalletController;
import javafx.stage.Stage;

public class OkButton {
    public void handle(BidderMiniWalletController controller) {
        String inputAmount = controller.getMaxBidField().getText();

        if (inputAmount == null || inputAmount.trim().isEmpty()) {
            Alert.showAlert("Lỗi Nhập Liệu", "Vui lòng nhập số tiền hạn mức tối đa.");
            return;
        }

        try {
            double maxBid = Double.parseDouble(inputAmount.trim());

            if (maxBid <= 0) {
                Alert.showAlert("Lỗi Giá Trị", "Số tiền hạn mức phải lớn hơn 0.");
                return;
            }

            if (maxBid > controller.getCurrentBalance()) {
                Alert.showAlert("Lỗi Số Dư", "Số tiền hạn mức vượt quá số dư tài khoản của bạn.\n" +
                        "Số dư hiện tại: " + controller.getCurrentBalance() + " $");
                return;
            }

            // Dữ liệu hợp lệ: Lưu hạn mức vào và đóng popup để luồng chính tiếp tục chạy
            controller.setChosenMaxBid(maxBid);

            Stage stage = (Stage) controller.getOkButton().getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            Alert.showAlert("Lỗi Định Dạng", "Định dạng số tiền không hợp lệ. Vui lòng chỉ nhập các chữ số.");
        } catch (Exception e) {
            Alert.showAlert("Lỗi Hệ Thống", "Đã xảy ra lỗi không mong muốn: " + e.getMessage());
        }
    }
}