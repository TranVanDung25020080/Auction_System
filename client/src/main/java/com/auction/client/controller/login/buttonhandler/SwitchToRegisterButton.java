package com.auction.client.controller.login.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchToRegisterButton {
    public void handle(ActionEvent event){

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
            Alert.showAlert("Lỗi", "Không thể tải giao diện đăng ký!");
        }

    }
}
