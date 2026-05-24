package com.auction.client.controller.admindashboard;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.network.http.AuctionApi;
import com.auction.client.network.http.UserApi;
import com.auction.common.dto.request.GetAuctionRequestDTO;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.GetBidInfoType;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.Auction.BidTransaction;
import com.auction.common.model.User.Bidder;
import com.auction.common.model.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminDashboardController {
    @FXML private Button btnUsers, btnAuctions;
    @FXML private VBox viewUsers, viewAuctions;
    @FXML private Label lblHeader;

    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User, Integer> colUserId;
    @FXML private TableColumn<User, String> colOwnerName, colUserName;
    @FXML private TableColumn<User, Double> colBalance;

    @FXML private TableView<Auction> tableAuctions;
    @FXML private TableColumn<Auction, Integer> colAuctionId;
    @FXML private TableColumn<Auction, String> colItemName;
    @FXML private TableColumn<Auction, Double> colCurrentPrice;
    @FXML private TableColumn<Auction, AuctionStatus> colAuctionStatus;

    @FXML private TableView<BidTransaction> tableTransactions;
    @FXML private TableColumn<BidTransaction, Integer> colTransId, colTransBidder;
    @FXML private TableColumn<BidTransaction, Double> colTransAmount;
    @FXML private TableColumn<BidTransaction, LocalDateTime> colTransTime; // Thêm cột thời gian

    public void initialize() {
        setupColumns();
        loadMockData();

        // --- QUAN TRỌNG: Lắng nghe sự kiện click dòng trên bảng Auction ---
        tableAuctions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Khi bấm vào 1 dòng, gọi hàm hiện lịch sử của Auction đó
                loadHistoryForAuction(newSelection.getAuctionId());
            }
        });

        btnUsers.setOnAction(e -> switchTab("USERS"));
        btnAuctions.setOnAction(e -> switchTab("AUCTIONS"));

        switchTab("USERS");
    }

    private void setupColumns() {
        // Cột User
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colOwnerName.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Cột Auction
        colAuctionId.setCellValueFactory(new PropertyValueFactory<>("auctionId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCurrentPrice.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        colAuctionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Cột Transactions (Lịch sử đặt giá)
        colTransId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colTransBidder.setCellValueFactory(new PropertyValueFactory<>("bidderId"));
        colTransAmount.setCellValueFactory(new PropertyValueFactory<>("bidAmount"));

        // Định dạng lại cột thời gian cho đẹp (HH:mm:ss)
        if (colTransTime != null) {
            colTransTime.setCellValueFactory(new PropertyValueFactory<>("bidTime"));
            colTransTime.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM")));
                    }
                }
            });
        }
    }

    private void loadHistoryForAuction(int auctionId) {
        try{
            //call api here:
            GetBidInfoRequestDTO getBidInfoRequestDTO=new GetBidInfoRequestDTO();
            getBidInfoRequestDTO.setAuctionId(auctionId);
            getBidInfoRequestDTO.setGetBidInfoType(GetBidInfoType.AUCTION_ID);


            GetBidInfoResponseDTO getBidInfoResponseDTO=new UserApi().getBidInfoByAuctionId(getBidInfoRequestDTO);
            List<BidTransaction> bidTransactionList=getBidInfoResponseDTO.getBidTransactionList();

            // Tạo dữ liệu thầu giả cho mỗi khi click
            if (bidTransactionList!=null){
                ObservableList<BidTransaction> history = FXCollections.observableArrayList(bidTransactionList);

                /*// Dữ liệu mẫu thay đổi dựa trên auctionId
                history.add(new BidTransaction(1001, auctionId, 7, 550.0 + auctionId, LocalDateTime.now().minusMinutes(10)));
                history.add(new BidTransaction(1002, auctionId, 12, 600.0 + auctionId, LocalDateTime.now().minusMinutes(5)));
                history.add(new BidTransaction(1003, auctionId, 7, 750.0 + auctionId, LocalDateTime.now().minusMinutes(1)));
*/
                tableTransactions.setItems(history);
                System.out.println("Đã hiển thị lịch sử cho sản phẩm mã: " + auctionId);
            }
        } catch (IOException e) {
            Alert.showAlert("ERROR",e.getMessage());
        }
    }

    private void switchTab(String tab) {
        boolean isUser = tab.equals("USERS");
        viewUsers.setVisible(isUser);
        viewAuctions.setVisible(!isUser);
        lblHeader.setText(isUser ? "Quản lý Người dùng" : "Quản lý Phiên đấu giá");

        // Style cho nút sidebar để biết đang ở tab nào
        btnUsers.getStyleClass().removeAll("menu-btn-active");
        btnAuctions.getStyleClass().removeAll("menu-btn-active");
        if(isUser) btnUsers.getStyleClass().add("menu-btn-active");
        else btnAuctions.getStyleClass().add("menu-btn-active");
    }

    private void loadMockData() {
        //Call api o day:

        try{
            //load userList:
            UserResponseDTO userResponseDTO=new UserApi().getAllUser();

            ObservableList<User> users = FXCollections.observableArrayList(userResponseDTO.getUserList());
            /*for(int i=1; i<=30; i++) users.add(new Bidder(i, "Khách hàng " + i, "user"+i, i*1200.0));*/
            tableUsers.setItems(users);

            //load auctionList:
            GetAuctionResponseDTO getAuctionResponseDTO=new AuctionApi().getAllAuction();

            ObservableList<Auction> auctions = FXCollections.observableArrayList(getAuctionResponseDTO.getAuctionList());
            /*for(int i=1; i<=20; i++) {
                auctions.add(new Auction(i, 100+i, 50+i, 500.0 + (i*10), 0, LocalDateTime.now(), LocalDateTime.now().plusHours(2), null, "Sản phẩm " + i));
            }*/
            tableAuctions.setItems(auctions);
        } catch (IOException e) {
            Alert.showAlert("ERROR",e.getMessage());
        }

    }
}