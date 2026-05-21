package com.auction.common.model.Auction;

import com.auction.common.enums.AuctionStatus;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Auction  {
    private int auctionId;
    private int itemId;
    private int sellerId;
    private double currentHighestPrice,startPrice;
    private int winningBidderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
    private String itemName;

    public Auction(int auctionId, int itemId, int sellerId, double currentHighestPrice, int winningBidderId,
                   LocalDateTime startTime, LocalDateTime endTime, AuctionStatus status,String itemName) {
        this.auctionId = auctionId;
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.currentHighestPrice = currentHighestPrice;
        this.winningBidderId = winningBidderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status ;
        this.itemName=itemName;
    }
    public Auction(){};
    public Auction(int itemId,int sellerId,String itemName,double startPrice,LocalDateTime startTime,LocalDateTime endTime){
        this.itemId=itemId;
        this.sellerId=sellerId;
        this.startPrice=startPrice;
        this.startTime=startTime;
        this.endTime=endTime;
        this.itemName=itemName;
        this.status=AuctionStatus.OPEN;
    }

    //Getter
    //region
    public int getItemId() {
        return itemId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public int getSellerId() { return sellerId; }

    public double getCurrentHighestPrice() {
        return currentHighestPrice;
    }

    public int getWinningBidderId() {
        return winningBidderId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public String getItemName(){
        return this.itemName;
    }

    public double getStartPrice() {
        return this.startPrice; // Trả về giá khởi điểm
    }

    public double getCurrentPrice() {
        return this.currentHighestPrice; // Trả về giá hiện tại
    }

    // Hàm này giúp fix lỗi ở ProductCardController (Ảnh image_295815.png)
    public Auction getItem() {
        return this;
    }

    public String getName() {
        return this.itemName;
    }





    public int getDurationLeft(){
        Duration duration=Duration.between(LocalDateTime.now(),endTime);

        return (int) duration.getSeconds();
    }
    //endregion
    //setter
    public void setCurrentHighestPrice(double newPrice){
        this.currentHighestPrice=newPrice;
    }
    public void endAuction(){
        this.status=AuctionStatus.FINISHED;
    }
    public void startAuction(){
        this.status=AuctionStatus.PENDING;
    }



    //Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", itemId=" + itemId +
                ", sellerId=" + sellerId +
                ", currentHighestPrice=" + currentHighestPrice +
                ", winningBidderId=" + winningBidderId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", itemName= "+itemName+
                '}';
    }

}
