package com.auction.client.controller.register.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.sun.net.httpserver.HttpExchange;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchToLoginButton {
    public void handle(ActionEvent event){
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
            Alert.showAlert("Lỗi hệ thống", "Không thể quay lại màn hình đăng nhập!");
        }
    }
}
