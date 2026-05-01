package com.auction.client.controller.register.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ResigterButton {
    public void handle(ActionEvent event, TextField txtDisplayName,TextField txtUsername,
                       TextField txtPassword){

        // Sử dụng .trim() để loại bỏ khoảng trắng thừa
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String displayName = txtDisplayName.getText().trim();

        // Kiểm tra nếu bất kỳ trường nào để trống
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            Alert.showAlert("Lỗi nhập liệu", "Vui lòng nhập đầy đủ thông tin, không được để trống trường nào!");
            return; // Dừng xử lý nếu thiếu thông tin
        }

        // Logic xử lý đăng ký (Sau này kết nối MySQL/JDBC tại đây)
        System.out.println("Đang tiến hành đăng ký cho: " + displayName);




        // Thông báo thành công
        Alert.showAlert("Thành công", "Tài khoản " + username + " đã được tạo thành công!");

        // Tùy chọn: Sau khi đăng ký xong có thể tự gọi switchToLogin(null) để về màn hình đăng nhập


    }

}
