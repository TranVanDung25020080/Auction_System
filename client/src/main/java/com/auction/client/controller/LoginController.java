package com.auction.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    // --- PHẦN BỔ SUNG ĐỂ CHUYỂN MÀN HÌNH ---

    @FXML
    public void handleSwitchToRegister(ActionEvent event) {
        try {
            // 1. Load file register.fxml
            // Lưu ý: Kiểm tra kỹ đường dẫn file fxml của bạn
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/register.fxml"));
            Parent registerRoot = loader.load();

            // 2. Lấy Stage hiện tại từ sự kiện nút bấm
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Tạo Scene mới và áp dụng CSS (nếu có)
            Scene scene = new Scene(registerRoot);
            String css = getClass().getResource("/com/auction/client/css/auth-styles.css").toExternalForm();
            scene.getStylesheets().add(css);

            // 4. Thay đổi màn hình
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải giao diện đăng ký!");
        }
    }

    // Logic đăng nhập hiện tại của bạn
    @FXML
    public void handleLogin(ActionEvent event) {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin!");
        } else {
            System.out.println("Đăng nhập với user: " + user);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}