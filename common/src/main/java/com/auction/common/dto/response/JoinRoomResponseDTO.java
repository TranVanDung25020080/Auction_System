package com.auction.common.dto.response;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ReponseType;

public class JoinRoomResponseDTO extends BaseResponse {
    private int userId;
    private int auctionRoomId;
    private AuctionStatus auctionStatus;
    //Constructor
    public JoinRoomResponseDTO(int userId,int auctionRoomId){
        this.userId=userId;
        this.auctionRoomId=auctionRoomId;
        this.responseType= ReponseType.JOIN_ROOM;
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

    @Override
    public String displayMessage() {
        return "User "+userId+" has joined room "+auctionRoomId;
    }
}
