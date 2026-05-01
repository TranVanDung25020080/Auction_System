package com.auction.common.dto.request;

public class BidRequestDTO {
    private int auctionId;
    private double bidAmount;


    public BidRequestDTO(int auctionId, double bidAmount) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
    }

    //Getter
    //region
    public int getAuctionId() {return auctionId;}
    public double getBidAmount() {return bidAmount;}
    //endregion
}
