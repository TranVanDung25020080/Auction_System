package com.auction.common.dto.response;

import com.auction.common.enums.BidStatus;
import com.auction.common.enums.ReponseType;

public class BidUpdateResponseDTO extends BaseResponse{
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
        this.responseType= ReponseType.BID_UPDATE;

    }
    public BidUpdateResponseDTO(int auctionId,int bidderId,double newHighestPrice){
        this.auctionId=auctionId;
        this.bidderId=bidderId;
        this.newHighestPrice=newHighestPrice;
        this.responseType= ReponseType.BID_UPDATE;
    }
    public BidUpdateResponseDTO(){
        this.responseType= ReponseType.BID_UPDATE;
    }

    //Getter
    //region
    public int getBidderId(){return this.bidderId;}
    public int getAuctionId() { return auctionId; }
    public double getNewHighestPrice() { return newHighestPrice; }
    public String getHighestBidderName() { return highestBidderName; }
    public String getTimeStamp() { return timeStamp; }
    public BidStatus getBidStatus(){
        return this.bidStatus;
    }
    //endregion

    //setter
    public void setBidStatus(BidStatus bidStatus){
        this.bidStatus=bidStatus;
    }
    public void setNewHighestPrice(double newHighestPrice){
        this.newHighestPrice=newHighestPrice;
    }
    public void setBidderId(int bidderId){
        this.bidderId=bidderId;
    }
    public void setAuctionId(int auctionId){
        this.auctionId=auctionId;
    }

    @Override
    public String displayMessage() {
        return "User "+bidderId+" has bidded "+newHighestPrice;
    }
}
