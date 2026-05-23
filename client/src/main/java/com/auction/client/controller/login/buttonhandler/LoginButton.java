package com.auction.client.controller.login.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.bidderdashboard.BidderDashboardController;
import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.client.network.http.LoginApi;
import com.auction.common.dto.request.LoginRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.UserRole;
import com.auction.common.model.User.Seller;
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
    private String ownerName,userName;
    private double balance;

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

            //set information for user:
            this.userId=userResponseDTO.getUserId();
            this.ownerName=userResponseDTO.getOwnerName();
            this.userName=userResponseDTO.getUserName();
            this.balance=userResponseDTO.getBalance();

            System.out.println(new Gson().toJson(userResponseDTO));

            AuthStatus authStatus=userResponseDTO.getAuthStatus();

            if (authStatus.equals(AuthStatus.SUCCESS)){

                //Chuyen toi bidderdashboard
                if (userResponseDTO.getUserRole()== UserRole.BIDDER){
                    switchToBidderDashboard(event);
                }
                //Chuyen toi seller dashboard
                else if (userResponseDTO.getUserRole()==UserRole.SELLER) {
                    switchToSellerDashboard(event);
                }


            }
            else {
                Alert.showAlert("ERORR",userResponseDTO.getMessage()+" "+userResponseDTO.getAuthStatus());

            }

        }

    }

    private void switchToBidderDashboard(ActionEvent event) {
        try {
            // Đảm bảo đường dẫn file FXML này là chính xác
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidder_dashboard.fxml"));
            Parent root = loader.load();
            BidderDashboardController bidderDashboardController=loader.getController();
            //Truyen thong tin cho bidderdashboard:

            bidderDashboardController.setBidder(this.userId,this.ownerName,this.ownerName,this.balance);
            bidderDashboardController.setBidderInfoTextField();

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
            Alert.showAlert("ERROR",e.getMessage());
            /*Alert.showAlert("Lỗi", "Không thể mở giao diện Dashboard! Kiểm tra lại đường dẫn file FXML.");*/
        }
    }

    private void switchToSellerDashboard(ActionEvent event) {
        try {
            // Đảm bảo đường dẫn này khớp 100% với cấu trúc project của bạn
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/seller_dashboard.fxml"));
            Parent root = loader.load();
            SellerDashboardController sellerDashboardController=loader.getController();
            sellerDashboardController.setSellerId(this.userId);

            // Tạo đối tượng Seller để nạp vào Dashboard (Giải quyết lỗi Null ban đầu)
            // Lưu ý: Nhấn Alt + Enter để Import class Seller nếu nó báo đỏ
            Seller loggedSeller = new Seller(this.userId, this.ownerName, this.userName, 0.0, this.balance);

            // Nạp dữ liệu vào Dashboard
            sellerDashboardController.setSellerData(loggedSeller);

            // Lấy Stage từ cái nút bấm
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Dashboard thường cần màn hình rộng (ví dụ 1100x750)
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("HỆ THỐNG QUẢN LÝ ĐẤU GIÁ - KÊNH CHỦ TÀI SẢN");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert.showAlert("Lỗi Giao Diện", e.getMessage());
        }

    }

}
