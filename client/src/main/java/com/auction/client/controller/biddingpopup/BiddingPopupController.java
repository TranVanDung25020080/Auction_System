package com.auction.client.controller.biddingpopup;

import com.auction.client.controller.biddingpopup.buttonhandler.AutoBidButton;
import com.auction.client.controller.biddingpopup.buttonhandler.ExitButton;
import com.auction.client.controller.biddingpopup.buttonhandler.NormalBidButton;
import com.auction.client.network.socket.ClientSocket;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.User.Bidder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BiddingPopupController {

    //FXML Fields:
    @FXML private Label lblId, lblName, lblBasePrice, lblCurrentPrice, lblCountdown, lblStatus,lblAnnouncement;
    @FXML private TextField txtNormalBid;
    @FXML private TextField txtMaxBid;      // fx:id phải khớp trong FXML
    @FXML private TextField txtIncrement;   // fx:id phải khớp trong FXML
    @FXML private Button normalBidButton,autoBidButton,exitButton;

    // Khai báo biểu đồ từ file fx:include
    @FXML private VBox priceChart; // ID này phải trùng với fx:id ở thẻ include
    @FXML private LineChart<String, Number> priceHistoryChart;


    // Biến quản lý dữ liệu đường kẻ trên biểu đồ
    private XYChart.Series<String, Number> priceSeries = new XYChart.Series<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    //Other fields:
    private Auction currentAuction;
    private Timeline timeline;
    private BidUpdateResponseDTO bidUpdateResponseDTO;
    private ClientSocket clientSocket;
    private int userId;
    private Bidder bidder;
    //init method which is gonna be called automaticly:
    public void initialize(){
        //set on action for buttons:
        this.normalBidButton.setOnAction(event ->
                new NormalBidButton().handle(this.txtNormalBid,this.currentAuction,this.lblStatus,
                        this));

        this.autoBidButton.setOnAction(event ->
                new AutoBidButton().handle(this.txtMaxBid,this.txtIncrement,this.lblStatus,this.currentAuction,this));

        this.exitButton.setOnAction(event ->
                new ExitButton().handle(this.timeline,this.lblId));
        //Khoi tao doi tuong dto :
        this.bidUpdateResponseDTO=new BidUpdateResponseDTO();

        // Khởi tạo biểu đồ: Gắn sợi dây dữ liệu vào bảng
        priceSeries.setName("Lịch sử giá");
        if (priceHistoryChart != null) {
            priceHistoryChart.getData().add(priceSeries);
        }

        // Thêm dòng log này để kiểm chứng
        if (priceHistoryChart != null) {
            System.out.println("CHÚC MỪNG: Biểu đồ đã được kết nối!");
            priceHistoryChart.getData().add(priceSeries);
            priceHistoryChart.setAnimated(false);
        } else {
            System.out.println("LỖI: Vẫn chưa tìm thấy biểu đồ. Hãy kiểm tra lại fx:id!");
        }
    }


    //Method for other classes to call:
    public void initData(Auction auction) {
        this.currentAuction = auction;

        // 1. Gán dữ liệu (Sử dụng String.valueOf để tránh lỗi int/double)
        lblId.setText(String.valueOf(auction.getAuctionId()));
        lblName.setText(auction.getItemName());
        lblBasePrice.setText(String.valueOf(auction.getStartPrice()));
        lblCurrentPrice.setText(String.valueOf(auction.getCurrentPrice()));

        // Vẽ điểm giá khởi điểm lên biểu đồ khi vừa vào phòng
        addPriceToChart(auction.getStartPrice());

        if (auction.getEndTime() != null) {
            startCountdown(auction.getEndTime());
        }

        // 2. Chạy đồng hồ đếm ngược
        if (auction.getEndTime() != null) {
            startCountdown(auction.getEndTime());
        } else {
            lblCountdown.setText("N/A");
        }
    }

    public void startCountdown(LocalDateTime endTime) {
        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            java.time.Duration duration = java.time.Duration.between(LocalDateTime.now(), endTime);

            if (duration.isNegative() || duration.isZero()) {
                lblCountdown.setText("Đã kết thúc");
                timeline.stop();
            } else {
                long hours = duration.toHours();
                int minutes = duration.toMinutesPart();
                int seconds = duration.toSecondsPart();
                lblCountdown.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void updateHighCurrentPrice(double newPrice){
        this.currentAuction.setCurrentHighestPrice(newPrice);
        this.bidUpdateResponseDTO.setNewHighestPrice(newPrice);

        // Cập nhật giao diện: Label và Chart
        setLabelCurrentPrice();
        addPriceToChart(newPrice);

    }

    /**
     * Thêm một tọa độ (Thời gian, Giá) vào biểu đồ và ép giao diện cập nhật
     */
    private void addPriceToChart(double price) {
        // Luôn sử dụng Platform.runLater để tránh lỗi Thread từ Socket
        Platform.runLater(() -> {
            String time = LocalTime.now().format(timeFormatter);

            // Thêm dữ liệu mới vào Series
            XYChart.Data<String, Number> newData = new XYChart.Data<>(time, price);
            priceSeries.getData().add(newData);

            // Giới hạn số điểm hiển thị để trục X không bị "nghẹt thở" (nên để 15-20 điểm)
            if (priceSeries.getData().size() > 20) {
                priceSeries.getData().remove(0);
            }

            // ÉP BIỂU ĐỒ VẼ LẠI: Đây là lệnh quan trọng nhất để thấy đường nối ngay lập tức
            if (priceHistoryChart != null) {
                priceHistoryChart.layout();
            }

            System.out.println("Đang vẽ giá"+price);
        });
    }
    public void setLabelAnnoucement(String text){
        this.lblAnnouncement.setText(text);
    }
    public void setLabelCurrentPrice(){
        this.lblCurrentPrice.setText(String.valueOf(this.currentAuction.getCurrentHighestPrice()));
    }
    public void setLblStatus(String text){
        this.lblStatus.setText(text);
    }
    public void setClientSocket(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }
    public ClientSocket getClientSocket(){
        return this.clientSocket;
    }
    public Auction getCurrentAuction(){
        return this.currentAuction;
    }
    public BidUpdateResponseDTO getBidUpdateResponseDTO(){
        return this.bidUpdateResponseDTO;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void endAuction(){
        this.currentAuction.endAuction();
    }
    public void startAuction(){
        this.currentAuction.startAuction();
    }
    public void setBidder(Bidder bidder){
        this.bidder=bidder;
    }
    public Bidder getBidder(){
        return this.bidder;
    }
    public void setEndTime(LocalDateTime endTime){
        this.currentAuction.setEndTime(endTime);
    }
}