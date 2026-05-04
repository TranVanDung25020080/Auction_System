package com.auction.client.controller.register;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.register.buttonhandler.ResigterButton;
import com.auction.client.controller.register.buttonhandler.SwitchToLoginButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class RegisterController {
    @FXML private TextField txtUsername, txtDisplayName;
    @FXML private PasswordField txtPassword;
    @FXML private Button registerButton, switchToLoginButton;

    @FXML private ToggleButton btnBidderRole;
    @FXML private ToggleButton btnOwnerRole;

    @FXML
    public void initialize() {
        if (btnBidderRole == null || btnOwnerRole == null) {
            System.err.println("LỖI: Không tìm thấy btnBidderRole hoặc btnOwnerRole trong FXML!");
            return;
        }

        ToggleGroup roleGroup = new ToggleGroup();
        btnBidderRole.setToggleGroup(roleGroup);
        btnOwnerRole.setToggleGroup(roleGroup);

        registerButton.setOnAction(event -> {
            String role = ""; // Dùng String luôn cho gọn thay vì StringBuilder

            if (btnBidderRole.isSelected()){
                role = "BIDDER";
            }
            else if (btnOwnerRole.isSelected()){
                role = "SELLER";
            }
            else {
                Alert.showAlert("Lỗi Đăng Ký!", "Vui lòng chọn vai trò của bạn trước khi đăng ký!");
                return; // QUAN TRỌNG: Lệnh return này sẽ CHẶN đứng việc chạy tiếp xuống dưới nếu chưa chọn Role
            }

            try {
                // Truyền cục dữ liệu sang cho thằng ResigterButton xử lý
                new ResigterButton().handle(event, txtDisplayName, txtUsername, txtPassword, role);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        switchToLoginButton.setOnAction(event -> new SwitchToLoginButton().handle(event));
    }
}