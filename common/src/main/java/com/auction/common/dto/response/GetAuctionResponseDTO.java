package com.auction.common.dto.response;

import com.auction.common.model.Auction.Auction;

import java.util.List;

public class GetAuctionResponseDTO {
    private Auction auction;
    private List<Auction> auctionList;
    private String message;
    public GetAuctionResponseDTO(Auction auction){
        this.auction=auction;
    }
    public GetAuctionResponseDTO(List<Auction> auctionList){
        this.auctionList=auctionList;
    }
    public GetAuctionResponseDTO(){}
    //setter
    public void setMessage(String message){
        this.message=message;
    }
    public void setAuctionList (List<Auction> auctionList){
        this.auctionList=auctionList;
    }
    //getter
    public List<Auction> getAuctionList(){
        return this.auctionList;
    }

}
