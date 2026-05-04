package com.auction.client.controller;

import com.auction.client.model.Auction;
import com.auction.client.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDateTime;

public class MyAuctionController {

    @FXML private TableView<Auction> auctionTable;
    @FXML private TableColumn<Auction, String> colName;
    @FXML private TableColumn<Auction, Double> colPrice;
    @FXML private TableColumn<Auction, Integer> colBids;
    @FXML private TableColumn<Auction, String> colTime;
    @FXML private TableColumn<Auction, String> colStatus;

    public void initialize() {
        // 1. Kết nối các cột với thuộc tính trong class Auction
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        colBids.setCellValueFactory(new PropertyValueFactory<>("bidCount"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // 2. Tạo dữ liệu giả để test (Đã đảo vị trí "ĐANG CHẠY" lên trước LocalDateTime)
        ObservableList<Auction> data = FXCollections.observableArrayList(
                new Auction("A1", new Item("1", "Đồng hồ Rolex", "", "", 0), 15000.0, 12, "ĐANG CHẠY", LocalDateTime.now().plusHours(2)),
                new Auction("A2", new Item("2", "Tranh sơn dầu", "", "", 0), 5000.0, 5, "ĐANG CHẠY", LocalDateTime.now().plusMinutes(30))
        );

        auctionTable.setItems(data);
    }
}