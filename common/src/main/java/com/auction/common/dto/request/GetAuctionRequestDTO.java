package com.auction.common.dto.request;

public class GetAuctionRequestDTO {
    private int autionId;
    private int sellerId;
    //Constructor
    public GetAuctionRequestDTO(int sellerId){
        this.sellerId=sellerId;
    }
    public GetAuctionRequestDTO(){};
    //getter
    public int getSellerId(){
        return this.sellerId;
    }
    public int getAutionId(){
        return this.autionId;
    }
    //setter
    public void setSellerId(int sellerId){
        this.sellerId=sellerId;
    }
    public void setAutionId(int autionId){
        this.autionId=autionId;
    }
}
