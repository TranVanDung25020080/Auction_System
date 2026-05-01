package com.auction.common.dto.request;

public class BidRequestDTO {
    private int auctionId;
    private double bidAmount;
    private double highCurrentPrice;


    public BidRequestDTO(int auctionId, double bidAmount,double highCurrentPrice) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
        this.highCurrentPrice=highCurrentPrice;
    }

    //Getter
    //region
    public int getAuctionId() {return auctionId;}
    public double getBidAmount() {return bidAmount;}
    //endregion
}
