package com.auction.common.model.Auction;

import com.auction.common.enums.AuctionStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Auction implements Serializable {
    private String auctionId;
    private String itemId;
    private double currentHighestPrice;
    private String winningBidderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;

    public Auction(String auctionId, String itemId, double currentHighestPrice, String winningBidderId, LocalDateTime startTime, LocalDateTime endTime, AuctionStatus status) {
        this.auctionId = auctionId;
        this.itemId = itemId;
        this.currentHighestPrice = currentHighestPrice;
        this.winningBidderId = winningBidderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status ;
    }

    //Getter
    //region
    public String getItemId() {
        return itemId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public double getCurrentHighestPrice() {
        return currentHighestPrice;
    }

    public String getWinningBidderId() {
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
    //endregion

    public String ToString() {
        return "starttime: " + startTime;
    }

}
