package com.auction.client.controller.login.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


public class LoginButton {
    public void handle(ActionEvent event, TextField txtUsername, TextField txtPassword) {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            Alert.showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin!");
        } else {
            System.out.println("Đăng nhập với user: " + user);
            //

        }


    }
}
