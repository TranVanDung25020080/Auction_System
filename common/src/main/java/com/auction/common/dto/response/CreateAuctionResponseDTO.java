package com.auction.common.dto.response;

import com.auction.common.model.Auction.Auction;

public class CreateAuctionResponseDTO {
    private Auction auction;
    private String message;
    //Constructor
    public CreateAuctionResponseDTO(){}
    public CreateAuctionResponseDTO(Auction auction,String message){
        this.auction=auction;
        this.message=message;
    }
    //getter
    public Auction getAuction(){
        return this.auction;
    }
    public String getMessage(){
        return this.message;
    }
    //setter
    public void setAuction(Auction auction){
        this.auction=auction;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
