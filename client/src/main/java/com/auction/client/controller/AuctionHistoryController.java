package com.auction.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AuctionHistoryController {

    @FXML private Label lblProductName;
    @FXML private TableView<Object> tableHistory; // Sau này thay Object bằng Model BidTransaction của bạn
    @FXML private TableColumn<Object, String> colTime;
    @FXML private TableColumn<Object, String> colBidder;
    @FXML private TableColumn<Object, Double> colPrice;

    /**
     * Hàm này để từ Card truyền tên sản phẩm và danh sách lịch sử sang
     */
    public void setAuctionData(String itemName) {
        lblProductName.setText(itemName);

        // TODO: Đổ dữ liệu từ List vào TableView
        // tableHistory.setItems(bidData);
    }

    /**
     * Xử lý đóng cửa sổ khi bấm nút Đóng
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) lblProductName.getScene().getWindow();
        stage.close();
    }
}