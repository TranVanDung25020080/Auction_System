package com.auction.common.dto.request;

import com.auction.common.enums.RequestType;

import java.time.LocalDateTime;

public class BidRequestDTO extends BaseRequestDTO{
    private int auctionId,bidderId;
    private double bidAmount;
    private double highCurrentPrice;
    private LocalDateTime endTime;
    private double maxBidDuringAuction;


    public BidRequestDTO(int bidderId ,int auctionId, double bidAmount,double highCurrentPrice) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
        this.highCurrentPrice=highCurrentPrice;
        this.bidderId=bidderId;
        this.requestType= RequestType.NORMAL_BIDDING;
    }
    public BidRequestDTO(int bidderId ,int auctionId, double bidAmount,double highCurrentPrice,LocalDateTime endTime,double maxBidDuringAuction) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
        this.highCurrentPrice=highCurrentPrice;
        this.bidderId=bidderId;
        this.requestType= RequestType.NORMAL_BIDDING;
        this.maxBidDuringAuction=maxBidDuringAuction;
        this.endTime=endTime;
    }
    public BidRequestDTO(int bidderId ,int auctionId, double bidAmount,double highCurrentPrice,LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
        this.highCurrentPrice=highCurrentPrice;
        this.bidderId=bidderId;
        this.requestType= RequestType.NORMAL_BIDDING;
        this.endTime=endTime;
    }
    public BidRequestDTO(){
        this.requestType= RequestType.NORMAL_BIDDING;
    }

    //Getter
    public int getBidderId(){return this.bidderId;}
    public int getAuctionId() {return auctionId;}
    public double getBidAmount() {return bidAmount;}
    public double getHighCurrentPrice(){return this.highCurrentPrice;}
    public LocalDateTime getEndTime(){
        return this.endTime;
    }

    public double getGetMaxBidDuringAuction() {
        return this.maxBidDuringAuction;
    }

    //setter
    public void setEndTime(LocalDateTime endTime){
        this.endTime=endTime;
    }
    public void setMaxBidDuringAuction(double maxBidDuringAuction){
        this.maxBidDuringAuction=maxBidDuringAuction;
    }
}
