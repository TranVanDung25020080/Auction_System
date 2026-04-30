package com.auction.client.controller.login.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.service.http.LoginApi;
import com.auction.common.dto.request.LoginRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginButton {
    public void handle(ActionEvent event, TextField txtUsername, TextField txtPassword) throws IOException {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            Alert.showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin!");
        } else {
            System.out.println("Đăng nhập với user: " + user);
            //Xu li call api
            LoginRequestDTO loginRequestDTO=new LoginRequestDTO(user,pass);

            UserResponseDTO userResponseDTO=new LoginApi().login(loginRequestDTO);

            System.out.println(new Gson().toJson(userResponseDTO));


        }


    }
}
