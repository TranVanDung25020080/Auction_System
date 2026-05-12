package com.auction.client.controller.sellerdashboard.buttonhandler;

import com.auction.client.controller.itemcardseller.ItemCardSellerController;
import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowIntentoryButton {
    private FlowPane flowPaneContent;

    public void handle(SellerDashboardController sellerDashboardController){
        Label lblHeader=sellerDashboardController.getLblHeader();
        this.flowPaneContent=sellerDashboardController.getFlowPaneContent();

        lblHeader.setText("KHO HÀNG CỦA TÔI");
        flowPaneContent.getChildren().clear();

        //Goi API Lay dach sach that :


        List<Item> mockItems = new ArrayList<>();

        // Fix lỗi Constructor: Truyền đúng thứ tự tham số theo ảnh bạn gửi
        // (id, name, description, price, seller(null), status, specialParam)
        mockItems.add(new Art(1, "Tranh Sơn Dầu Phố Cổ", "Tranh vẽ tay 2023", 5000000.0, null, ItemStatus.AVAILABLE, "Họa sĩ Trần Văn A"));
        mockItems.add(new Vehicle(2, "VinFast VF8", "Xe lướt 2000km", 800000000.0, null, ItemStatus.AVAILABLE, "VinFast"));
        mockItems.add(new Electronics(3, "MacBook Pro M3", "Nguyên seal", 45000000.0, null, ItemStatus.AVAILABLE, 12));

        renderInventory(mockItems);
        System.out.println("Đã tải dữ liệu giả lập kho hàng.");
    }

    private void renderInventory(List<Item> itemList) {
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
}
