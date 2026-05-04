package com.auction.client.controller.login.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.bidderdashboard.BidderDashboardController;
import com.auction.client.service.http.LoginApi;
import com.auction.common.dto.request.LoginRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginButton {
    private int userId;

    public void handle(ActionEvent event, TextField txtUsername, TextField txtPassword) throws IOException {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            Alert.showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin!");
        } else {
            System.out.println("Dang nhap voi user: " + user);

            //Xu li call api
            LoginRequestDTO loginRequestDTO=new LoginRequestDTO(user,pass);
            UserResponseDTO userResponseDTO=new LoginApi().login(loginRequestDTO);

            //set userId
            this.userId=userResponseDTO.getUserId();

            System.out.println(new Gson().toJson(userResponseDTO));

            AuthStatus authStatus=userResponseDTO.getAuthStatus();

            if (authStatus.equals(AuthStatus.SUCCESS)){
                switchToDashboard(event);
            }
            else {
                Alert.showAlert("ERORR",userResponseDTO.getMessage()+" "+userResponseDTO.getAuthStatus());

            }

        }

    }

    private void switchToDashboard(ActionEvent event) {
        try {
            // Đảm bảo đường dẫn file FXML này là chính xác
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidder_dashboard.fxml"));
            Parent root = loader.load();

            //truyen userId cho bidderdashboardcontroller
            BidderDashboardController bidderDashboardController=loader.getController();
            bidderDashboardController.setUserId(this.userId);

            // Lấy Stage từ event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tạo Scene mới
            Scene scene = new Scene(root);

            // Gắn CSS Dashboard (Đảm bảo file CSS tồn tại tại đường dẫn này)
            try {
                String css = getClass().getResource("/com/auction/client/css/dashboard-styles.css").toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception e) {
                System.out.println("Không tìm thấy file CSS dashboard-styles.css, bỏ qua gắn CSS.");
            }

            stage.setScene(scene);
            stage.setTitle("AUCTION PRO - DASHBOARD");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("Lỗi", "Không thể mở giao diện Dashboard! Kiểm tra lại đường dẫn file FXML.");
        }
    }

}
