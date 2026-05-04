package com.auction.common.model.Auction;

import com.auction.common.enums.AuctionStatus;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Auction implements Serializable {
    private int auctionId;
    private int itemId;
    private double currentHighestPrice;
    private int winningBidderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
    private String itemName;

    public Auction(int auctionId, int itemId, double currentHighestPrice, int winningBidderId,
                   LocalDateTime startTime, LocalDateTime endTime, AuctionStatus status,String itemName) {
        this.auctionId = auctionId;
        this.itemId = itemId;
        this.currentHighestPrice = currentHighestPrice;
        this.winningBidderId = winningBidderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status ;
        this.itemName=itemName;
    }

    //Getter
    //region
    public int getItemId() {
        return itemId;
    }

    public int getAuctionId() {
        return auctionId;
    }

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
    public int getDurationLeft(){
        Duration duration=Duration.between(startTime,endTime);

        return (int) duration.getSeconds();
    }
    //endregion

    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", itemId=" + itemId +
                ", currentHighestPrice=" + currentHighestPrice +
                ", winningBidderId=" + winningBidderId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", itemName= "+itemName+
                '}';
    }

}
