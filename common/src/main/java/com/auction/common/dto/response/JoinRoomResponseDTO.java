package com.auction.common.dto.response;

import com.auction.common.enums.AuctionStatus;

public class JoinRoomResponseDTO {
    private int userId;
    private AuctionStatus auctionStatus;
    //Constructor
    public JoinRoomResponseDTO(int userId){
        this.userId=userId;
    }

    //getter
    public int getUserId(){
        return this.userId;
    }
    public AuctionStatus getAuctionStatus(){
        return this.auctionStatus;
    }

    //setter
    public void setUserId(int userId){
        this.userId=userId;
    }
    public void setAuctionStatus(AuctionStatus auctionStatus){
        this.auctionStatus=auctionStatus;
    }

}
