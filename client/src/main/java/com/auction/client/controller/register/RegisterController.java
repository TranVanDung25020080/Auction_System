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

    // Đảm bảo 2 fx:id này khớp 100% với Scene Builder
    @FXML private ToggleButton btnBidderRole;
    @FXML private ToggleButton btnOwnerRole;

    @FXML
    public void initialize() {
        // Kiểm tra an toàn để xem nút có bị null không
        if (btnBidderRole == null || btnOwnerRole == null) {
            System.err.println("LỖI: Không tìm thấy btnBidderRole hoặc btnOwnerRole trong FXML!");
            return;
        }

        ToggleGroup roleGroup = new ToggleGroup();
        btnBidderRole.setToggleGroup(roleGroup);
        btnOwnerRole.setToggleGroup(roleGroup);

        registerButton.setOnAction(event -> {
            StringBuilder role=new StringBuilder();

            if (btnBidderRole.isSelected()){
                role.append("BIDDER");
            }
            else if (btnOwnerRole.isSelected()){
                role.append("SELLER");
            }
            else{
                Alert.showAlert("Register Error!","Select your role first");
            }

            try {
                new ResigterButton().handle(event, txtDisplayName, txtUsername, txtPassword, role.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        switchToLoginButton.setOnAction(event -> new SwitchToLoginButton().handle(event));
    }
}