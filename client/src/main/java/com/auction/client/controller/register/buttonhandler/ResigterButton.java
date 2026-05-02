package com.auction.client.controller.register.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.service.http.SignUpApi;
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

        /*// 4. QUAN TRỌNG: Gọi hàm chuyển cảnh ngay tại đây
        switchToDashboard(event);*/
    }

    private void switchToDashboard(ActionEvent event) {
        try {
            // Đảm bảo đường dẫn file FXML này là chính xác
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidder_dashboard.fxml"));
            Parent root = loader.load();

            // Lấy Stage từ event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tạo Scene mới
            Scene scene = new Scene(root);

            // Gắn CSS Dashboard (Đảm bảo file CSS tồn tại tại đường dẫn này)
            try {
                String css = getClass().getResource("/com/auction/client/css/dashboard-styles.css").toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception e) {
                System.out.println("Không tìm thấy file CSS dashboard-styles.css, bỏ qua gắn CSS.");
            }

            stage.setScene(scene);
            stage.setTitle("AUCTION PRO - DASHBOARD");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("Lỗi", "Không thể mở giao diện Dashboard! Kiểm tra lại đường dẫn file FXML.");
        }
    }
}