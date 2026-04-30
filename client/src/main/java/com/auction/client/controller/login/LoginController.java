package com.auction.client.controller.login;

import com.auction.client.controller.login.buttonhandler.LoginButton;
import com.auction.client.controller.login.buttonhandler.SwitchToRegisterButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin,btnRegister;

    public void initialize(){
        btnLogin.setOnAction(event -> new LoginButton().handle(event,txtUsername,txtPassword));

        btnRegister.setOnAction(event -> new SwitchToRegisterButton().handle(event));
    }
}