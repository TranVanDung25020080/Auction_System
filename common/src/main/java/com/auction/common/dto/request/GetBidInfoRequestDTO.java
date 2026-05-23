package com.auction.common.dto.request;

import com.auction.common.enums.GetBidInfoType;

public class GetBidInfoRequestDTO {
    private int bidderId;
    private int auctionId;
    private GetBidInfoType getBidInfoType;
    //Constructor
    public GetBidInfoRequestDTO(){};

    public GetBidInfoRequestDTO(int bidderId){
        this.bidderId=bidderId;
    }

    public GetBidInfoRequestDTO(int bidderId,int auctionId){
        this.bidderId=bidderId;
        this.auctionId=auctionId;
    }
    //getter
    public int getBidderId(){
        return this.bidderId;
    }
    public int getAuctionId(){
        return this.auctionId;
    }
    public GetBidInfoType getBidInfoType(){
        return this.getBidInfoType;
    }
    //setter
    public void setBidderId(int bidderId){
        this.bidderId=bidderId;
    }
    public void setAuctionId(int auctionId){
        this.auctionId=auctionId;
    }
    public void setGetBidInfoType(GetBidInfoType getBidInfoType){
        this.getBidInfoType=getBidInfoType;
    }
}
