package com.auction.common.dto.response;

import com.auction.common.enums.BidStatus;

public class BidUpdateResponseDTO {
    private int auctionId,bidderId;
    private double newHighestPrice;
    private String highestBidderName;
    private String timeStamp;
    private BidStatus bidStatus;

    //Constructor
    public BidUpdateResponseDTO(int auctionId, double newHighestPrice, String highestBidderName, String timeStamp) {
        this.auctionId = auctionId;
        this.newHighestPrice = newHighestPrice;
        this.highestBidderName = highestBidderName;
        this.timeStamp = timeStamp;
    }
    public BidUpdateResponseDTO(int auctionId,int bidderId,double newHighestPrice){
        this.auctionId=auctionId;
        this.bidderId=bidderId;
        this.newHighestPrice=newHighestPrice;
    }
    public BidUpdateResponseDTO(){}

    //Getter
    //region
    public int getBidderId(){return this.bidderId;}
    public int getAuctionId() { return auctionId; }
    public double getNewHighestPrice() { return newHighestPrice; }
    public String getHighestBidderName() { return highestBidderName; }
    public String getTimeStamp() { return timeStamp; }
    //endregion

    //setter
    public void setBidStatus(BidStatus bidStatus){
        this.bidStatus=bidStatus;
    }
}
