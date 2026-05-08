package com.auction.common.model.Auction;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BidTransaction implements Serializable {
    private int transactionId;
    private int auctionId;
    private int bidderId;
    private double bidAmount;
    private LocalDateTime bidTime;

    public BidTransaction() {}

    public BidTransaction(int transactionId, int auctionId, int bidderId, double bidAmount, LocalDateTime bidTime) {
        this.transactionId = transactionId;
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
    }

    //region
    public int getTransactionId() { return transactionId; }
    public int getAuctionId() { return auctionId; }
    public int getBidderId() { return bidderId; }
    public double getBidAmount() { return bidAmount; }
    public LocalDateTime getBidTime() { return bidTime; }
    //endregion

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    @Override
    public String toString() {
        return "BidTransaction{" +
                "id=" + transactionId +
                ", auction=" + auctionId +
                ", bidder=" + bidderId +
                ", amount=" + bidAmount +
                ", time=" + bidTime +
                '}';
    }
}
