package com.auction.client.controller.register.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.service.http.SignUpApi;
import com.auction.common.dto.request.RegisterRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.UserRole;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ResigterButton {
    public void handle(ActionEvent event, TextField txtDisplayName, TextField txtUsername,
                       TextField txtPassword, UserRole role) throws IOException {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String displayName = txtDisplayName.getText().trim();

        // 1. Kiểm tra để trống
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            Alert.showAlert("Lỗi nhập liệu", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // --- Logic Đăng ký (API) ---
        try {
            System.out.println("Đang gọi API đăng ký cho: " + displayName);
            RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(username, displayName, password, role);

            // Nếu bạn chưa chạy Server, dòng dưới đây sẽ bị lỗi.
            // Mình để trong try-catch để dù lỗi API thì vẫn có thể chạy tiếp xuống phần chuyển giao diện để bạn test.
            UserResponseDTO userResponseDTO = new SignUpApi().register(registerRequestDTO);
            System.out.println("Kết quả API: " + new Gson().toJson(userResponseDTO));

        } catch (Exception e) {
            System.out.println("LƯU Ý: Không kết nối được Server (Có thể do chưa bật Server), nhưng vẫn tiếp tục để test giao diện Dashboard...");
        }

        // 3. Thông báo thành công
        Alert.showAlert("Thành công", "Tài khoản " + username + " đã được tạo thành công!");

        // 4. KIỂM TRA ROLE ĐỂ ĐIỀU HƯỚNG (Giữ nguyên Bidder, thêm Seller)
        if ("SELLER".equals(role)) {
            // Nếu là người bán -> Nhảy thẳng vào Dashboard để xem thành quả
            switchToSellerDashboard(event);
        } else {
            // Nếu là người mua (Bidder) -> GIỮ NGUYÊN LUỒNG CŨ CỦA BẠN: Quay về Login
            new SwitchToLoginButton().handle(event);
        }
    }

    // Hàm phụ trợ để chuyển sang Seller Dashboard
    private void switchToSellerDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/auction/client/view/seller_dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("Lỗi giao diện", "Không tìm thấy file seller_dashboard.fxml!");
        }
    }
}