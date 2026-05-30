package com.auction.client.controller.productcard.buttonhandler;

import com.auction.client.controller.bidderdashboard.BidderDashboardController;
import com.auction.client.controller.bidderminiwallet.BidderMiniWalletController;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.client.service.AuctionRoomService;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.User.Bidder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinRoomButton {
    /*public void handle(Auction auctionData, int userId, Bidder bidder){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidding_popup.fxml"));
            Parent root = loader.load();

            // Sửa lại theo đúng package chứa BiddingPopupController (nếu cần)
            BiddingPopupController popupController = loader.getController();
            popupController.initData(auctionData);
            popupController.setUserId(userId);
            popupController.setBidder(bidder);

            popupController.startAuction();





            //Goi den service broadcast khi joinRoom:

            ClientSocket clientSocket =new AuctionRoomService().joinRoom(userId,auctionData.getAuctionId(),popupController);
            popupController.setClientSocket(clientSocket); //truyen vao clientsocket cho biddingpoupcontroller

            //Hien thi cho user:
            Stage stage = new Stage();
            stage.setScene(new Scene(root,1100,800));
*//*            stage.initModality(Modality.APPLICATION_MODAL);*//*

            stage.setResizable(true);
            stage.setMinWidth(900);
            stage.setMinHeight(700);

            stage.setTitle("Chi tiết phiên đấu giá");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi mở Popup: " + e.getMessage());
        }

    }*/


    // Cai nay la gemini lam:))
    // Bổ sung thêm tham số BidderDashboardController để cập nhật UI dashboard sau khi trừ tiền
    public void handle(Auction auctionData, int userId, Bidder bidder, BidderDashboardController dashboardController) {
        try {
            // Bước 1: Khởi tạo và hiển thị Popup BidderMiniWallet trước
            FXMLLoader walletLoader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidder_miniwallet.fxml"));
            Parent walletRoot = walletLoader.load();

            BidderMiniWalletController walletController = walletLoader.getController();
            // Truyền số dư thực tế của bidder vào ví phòng đấu giá
            walletController.setData(bidder.getBalance());

            Stage walletStage = new Stage();
            walletStage.setScene(new Scene(walletRoot));
            walletStage.setTitle("Thiết lập hạn mức ví phòng");
            walletStage.initModality(Modality.APPLICATION_MODAL); // Khóa màn hình chính cho tới khi xử lý xong ví
            walletStage.showAndWait(); // Đợi người dùng bấm OK hoặc Cancel

            // Bước 2: Kiểm tra kết quả thiết lập hạn mức từ ví phòng đấu giá
            double chosenMaxBid = walletController.getChosenMaxBid();

            // Nếu chosenMaxBid > 0 tức là người dùng đã nhập hợp lệ và bấm OK thành công
            if (chosenMaxBid > 0) {

                // 1. Trừ tiền tài khoản của đối tượng bidder thực tế trong phiên chạy
                // call api here:
                double newBalance = bidder.getBalance() - chosenMaxBid;
                bidder.setBalance(newBalance);


                // 2. Cập nhật hiển thị số dư mới lên thanh trạng thái thông tin của BidderDashboard
                dashboardController.setBidderInfoTextField();

                // 3. Thực hiện logic cũ của JoinRoom để mở cửa sổ phòng đấu giá chính thức
                openBiddingRoom(auctionData, userId, bidder, chosenMaxBid);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi quy trình mở ví phòng hoặc phòng đấu giá: " + e.getMessage());
        }
    }

    /**
     * Logic mở phòng đấu giá (Được tách từ code cũ của bạn sang)
     */
    private void openBiddingRoom(Auction auctionData, int userId, Bidder bidder, double maxBid) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/bidding_popup.fxml"));
            Parent root = loader.load();

            BiddingPopupController popupController = loader.getController();
            popupController.initData(auctionData);
            popupController.setUserId(userId);
            popupController.setBidder(bidder);

            // Thiết lập hạn mức ví mini vừa chọn vào phiên đấu giá hiện tại
            popupController.setMaxBidDuringAuction(maxBid);

            popupController.startAuction();

            // Gọi dịch vụ kết nối socket room
            ClientSocket clientSocket = new AuctionRoomService().joinRoom(userId, auctionData.getAuctionId(), popupController);
            popupController.setClientSocket(clientSocket);

            // Hiển thị giao diện phòng đấu giá cho user
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1100, 800));
            stage.setResizable(true);
            stage.setMinWidth(900);
            stage.setMinHeight(700);
            stage.setTitle("Chi tiết phiên đấu giá");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi mở giao diện phòng đấu giá!");
        }
    }


}
