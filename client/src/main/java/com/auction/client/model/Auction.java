package com.auction.client.model;

import java.time.LocalDateTime;

public class Auction {
    private String auctionId;
    private Item item; // Phiên đấu giá này cho món đồ nào
    private double currentPrice;
    private int totalBids;
    private String status; // "ĐANG_DIEN_RA", "THANH_CONG", "THAT_BAI"
    private LocalDateTime endTime;

    public Auction(String auctionId, Item item, double currentPrice, int totalBids, String status, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.item = item;
        this.currentPrice = currentPrice;
        this.totalBids = totalBids;
        this.status = status;
        this.endTime = endTime;
    }

    // Getters
    public Item getItem() { return item; }
    public double getCurrentPrice() { return currentPrice; }
    public String getStatus() { return status; }
}
