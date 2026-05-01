package com.auction.client.controller.register;

import com.auction.client.controller.register.buttonhandler.ResigterButton;
import com.auction.client.controller.register.buttonhandler.SwitchToLoginButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField; // Dùng PasswordField cho mật khẩu
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    //FXML fields
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtDisplayName;
    @FXML private Button registerButton,switchToLoginButton;

    public void initialize(){
        //set on action for buttons
        registerButton.setOnAction(event -> new ResigterButton().handle(event,txtDisplayName,txtUsername,txtPassword));

        switchToLoginButton.setOnAction(event -> new SwitchToLoginButton().handle(event));

    }

}