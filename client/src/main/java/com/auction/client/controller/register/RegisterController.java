package com.auction.client.controller.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField; // Dùng PasswordField cho mật khẩu
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML private TextField txtUsername;

    // Đã đổi sang PasswordField để khớp với bản chất mật khẩu
    @FXML private PasswordField txtPassword;

    @FXML private TextField txtDisplayName;

    @FXML
    public void switchToLogin(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/auction/client/view/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(loginRoot);

            // Đảm bảo file CSS tồn tại tại đường dẫn này
            String css = getClass().getResource("/com/auction/client/css/auth-styles.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi hệ thống", "Không thể quay lại màn hình đăng nhập!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleFinalizeRegistration() {
        // Sử dụng .trim() để loại bỏ khoảng trắng thừa
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String displayName = txtDisplayName.getText().trim();

        // Kiểm tra nếu bất kỳ trường nào để trống
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            showAlert("Lỗi nhập liệu", "Vui lòng nhập đầy đủ thông tin, không được để trống trường nào!", Alert.AlertType.WARNING);
            return; // Dừng xử lý nếu thiếu thông tin
        }

        // Logic xử lý đăng ký (Sau này kết nối MySQL/JDBC tại đây)
        System.out.println("Đang tiến hành đăng ký cho: " + displayName);

        // Thông báo thành công
        showAlert("Thành công", "Tài khoản " + username + " đã được tạo thành công!", Alert.AlertType.INFORMATION);

        // Tùy chọn: Sau khi đăng ký xong có thể tự gọi switchToLogin(null) để về màn hình đăng nhập
    }

    // Hàm showAlert cải tiến để bạn có thể chọn loại biểu tượng (Lỗi, Cảnh báo, Thông tin)
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}