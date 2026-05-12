package com.auction.client.controller.register.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.network.http.SignUpApi;
import com.auction.common.dto.request.RegisterRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
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
                       TextField txtPassword, String role) throws IOException {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String displayName = txtDisplayName.getText().trim();

        // 1. Kiểm tra để trống
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            Alert.showAlert("Lỗi nhập liệu", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // --- Logic Đăng ký (API/Database) viết ở đây ---
        System.out.println("Đăng ký thành công cho: " + displayName);

        //call api:
        RegisterRequestDTO registerRequestDTO=new RegisterRequestDTO(username,displayName,password,role);

        UserResponseDTO userResponseDTO=new SignUpApi().register(registerRequestDTO);

        System.out.println(new Gson().toJson(userResponseDTO));



        // 3. Thông báo thành công
        Alert.showAlert("Thành công", "Tài khoản " + username + " đã được tạo thành công!");

        new SwitchToLoginButton().handle(event);

/*        // 3. Logic chuyển cảnh thông minh
        if (role.equalsIgnoreCase("SELLER")) {
            // Nếu là Seller -> Chuyển thẳng vào Dashboard để test giao diện
            Alert.showAlert("Thành công", "Đăng ký Seller thành công! Đang vào Dashboard...");
            switchToSellerDashboard(event);
        } else {
            // Nếu là Bidder -> Quay về Login theo đúng logic cũ của bạn
            Alert.showAlert("Thành công", "Đăng ký Bidder thành công! Vui lòng đăng nhập lại.");
            new SwitchToLoginButton().handle(event);
        }*/
    }

/*    private void switchToSellerDashboard(ActionEvent event) {
        try {
            // Đảm bảo đường dẫn này khớp 100% với cấu trúc project của bạn
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/seller_dashboard.fxml"));
            Parent root = loader.load();

            // Lấy Stage từ cái nút bấm
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Dashboard thường cần màn hình rộng (ví dụ 1100x750)
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("HỆ THỐNG QUẢN LÝ ĐẤU GIÁ - KÊNH CHỦ TÀI SẢN");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("Lỗi Giao Diện", "Không tìm thấy file seller_dashboard.fxml!");
        }

    }*/


}