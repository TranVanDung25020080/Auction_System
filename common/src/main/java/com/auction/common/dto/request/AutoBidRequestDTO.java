package com.auction.common.dto.request;

import com.auction.common.enums.RequestType;

import java.time.LocalDateTime;

public class AutoBidRequestDTO extends BaseRequestDTO{
    private int auctionId;
    private int bidderId;
    private double maxBid;
    private double increment;
    private String created_at;
    private double highCurrentPrice;

    //Constructor:
    public AutoBidRequestDTO(int auctionId,int bidderId,double maxBid,double increment,double highCurrentPrice){
        this.auctionId=auctionId;
        this.bidderId=bidderId;
        this.maxBid=maxBid;
        this.increment=increment;
        this.highCurrentPrice=highCurrentPrice;
        this.requestType= RequestType.AUTO_BIDDING;
    }
    public AutoBidRequestDTO(){
        this.requestType=RequestType.AUTO_BIDDING;
    }
    //getter
    public int getAuctionId(){
        return this.auctionId;
    }
    public int getBidderId(){
        return this.bidderId;
    }
    public double getMaxBid(){
        return this.maxBid;
    }
    public double getIncrement(){
        return this.increment;
    }
    public String getCreated_at(){
        return this.created_at;
    }
    public double getHighCurrentPrice(){
        return this.highCurrentPrice;
    }
    //setter
    public void setAuctionId(int auctionId){
        this.auctionId=auctionId;
    }
    public void setBidderId(int bidderId){
        this.bidderId=bidderId;
    }
    public void setMaxBid(double maxBid){
        this.maxBid=maxBid;
    }
    public void setIncrement(double increment){
        this.increment=increment;
    }
    public void setCreated_at(String localDateTime){
        this.created_at=localDateTime;
    }
    public void setHighCurrentPrice(double highCurrentPrice){
        this.highCurrentPrice=highCurrentPrice;
    }
}
