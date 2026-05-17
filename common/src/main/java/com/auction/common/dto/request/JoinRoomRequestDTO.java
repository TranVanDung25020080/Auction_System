package com.auction.common.dto.request;

public class JoinRoomRequestDTO {
    private int userId;
    private int auctionId;
    public JoinRoomRequestDTO(int userId, int auctionId){
        this.userId=userId;
        this.auctionId=auctionId;
    }
    //getter
    public int getAuctionId(){
        return this.auctionId;
    }
    public int getUserId(){
        return this.userId;
    }
}
