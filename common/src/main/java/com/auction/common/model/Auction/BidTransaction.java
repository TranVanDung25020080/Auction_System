package com.auction.common.model.Auction;

import java.time.LocalDateTime;

public class BidTransaction {
    private String transactionId;
    private String auctionId;
    private String bidderId;
    private double bidAmount;
    private LocalDateTime bidTime;

    public BidTransaction(String transactionId, String auctionId, String bidderId, double bidAmount, LocalDateTime bidTime) {
        this.transactionId = transactionId;
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
    }

    //region
    public String getTransactionId() { return transactionId; }
    public String getAuctionId() { return auctionId; }
    public String getBidderId() { return bidderId; }
    public double getBidAmount() { return bidAmount; }
    public LocalDateTime getBidTime() { return bidTime; }
    //endregion

}
