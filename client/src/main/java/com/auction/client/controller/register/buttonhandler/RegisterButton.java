package com.auction.client.controller.register.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.network.http.SignUpApi;
import com.auction.common.dto.request.RegisterRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterButton {
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

        //Logic Đăng ký (API/Database)
        System.out.println("Đăng ký thành công cho: " + displayName);

        //call api:
        RegisterRequestDTO registerRequestDTO=new RegisterRequestDTO(username,displayName,password,role);

        UserResponseDTO userResponseDTO=new SignUpApi().register(registerRequestDTO);

        System.out.println(new Gson().toJson(userResponseDTO));



        // 3. Thông báo thành công
        Alert.showAlert("Thành công", "Tài khoản " + username + " đã được tạo thành công!");

        new SwitchToLoginButton().handle(event);

    }
}