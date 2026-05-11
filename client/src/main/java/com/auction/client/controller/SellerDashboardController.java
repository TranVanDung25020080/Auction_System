package com.auction.client.controller;

import com.auction.client.controller.ItemCardSellerController;
import com.auction.client.controller.AuctionCardSellerController;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SellerDashboardController {

    @FXML private FlowPane flowPaneContent;
    @FXML private Label lblHeader;

    @FXML
    public void initialize() {
        // Tự động load kho hàng khi vừa vào dashboard
        showInventory();
    }

    // --- 1. CHỨC NĂNG HIỂN THỊ KHO HÀNG ---
    @FXML
    private void showInventory() {
        lblHeader.setText("KHO HÀNG CỦA TÔI");
        flowPaneContent.getChildren().clear();

        List<Item> mockItems = new ArrayList<>();

        // Fix lỗi Constructor: Truyền đúng thứ tự tham số theo ảnh bạn gửi
        // (id, name, description, price, seller(null), status, specialParam)
        mockItems.add(new Art(1, "Tranh Sơn Dầu Phố Cổ", "Tranh vẽ tay 2023", 5000000.0, null, ItemStatus.AVAILABLE, "Họa sĩ Trần Văn A"));
        mockItems.add(new Vehicle(2, "VinFast VF8", "Xe lướt 2000km", 800000000.0, null, ItemStatus.AVAILABLE, "VinFast"));
        mockItems.add(new Electronics(3, "MacBook Pro M3", "Nguyên seal", 45000000.0, null, ItemStatus.AVAILABLE, 12));

        renderInventory(mockItems);
        System.out.println("Đã tải dữ liệu giả lập kho hàng.");
    }

    public void renderInventory(List<Item> itemList) {
        flowPaneContent.getChildren().clear();
        for (Item item : itemList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/item_card_seller.fxml"));
                VBox card = loader.load();

                ItemCardSellerController controller = loader.getController();
                controller.setItemData(item);

                flowPaneContent.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // --- 2. CHỨC NĂNG XEM CÁC CUỘC ĐẤU GIÁ ĐANG DIỄN RA ---
    @FXML
    private void showLiveAuctions() {
        lblHeader.setText("PHIÊN ĐẤU GIÁ ĐANG CHẠY");
        flowPaneContent.getChildren().clear();

        // Tạo 1 Auction giả để test đồng hồ và giá
        Auction mockAuction = new Auction(
                101, 1, 5500000.0, 12,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5), // Kết thúc sau 5 phút
                AuctionStatus.OPEN,
                "Tranh Sơn Dầu Phố Cổ"
        );

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/auction_card_seller.fxml"));
            VBox card = loader.load();

            // Lấy controller của Auction Card
            AuctionCardSellerController controller = loader.getController();
            controller.setAuctionData(mockAuction);

            flowPaneContent.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- 3. MỞ POPUP THÊM SẢN PHẨM MỚI ---
    @FXML
    private void openAddItemPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/add_item_popup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Thêm sản phẩm mới");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Lỗi mở popup: " + e.getMessage());
        }
    }
}