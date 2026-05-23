package com.auction.client.controller.auctionhistory;

import com.auction.common.model.Auction.BidTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AuctionHistoryController {

    @FXML private Label lblProductName;
    @FXML private TableView<BidTransaction> tableHistory; // Sau này thay Object bằng Model BidTransaction của bạn
    @FXML private TableColumn<BidTransaction, LocalDateTime> colTime;
    @FXML private TableColumn<BidTransaction, Integer> colBidder;
    @FXML private TableColumn<BidTransaction, Double> colPrice;


    @FXML
    public void initialize() {

        colTime.setCellValueFactory(new PropertyValueFactory<>("bidTime"));
        colBidder.setCellValueFactory(new PropertyValueFactory<>("bidderId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("bidAmount"));
    }

    /**
     * Hàm này để từ Card truyền tên sản phẩm và danh sách lịch sử sang
     */
    public void setAuctionData(String itemName, List<BidTransaction> bidData) {
        lblProductName.setText(itemName);

        // TODO: Đổ dữ liệu từ List vào TableView
        //tableHistory.setItems(bidData);
        if (bidData != null) {
            // 2. Chuyển đổi List thường thành ObservableList để JavaFX TableView có thể hiểu được
            ObservableList<BidTransaction> observableList = FXCollections.observableArrayList(bidData);

            // 3. Đổ dữ liệu vào TableView
            tableHistory.setItems(observableList);
        }




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