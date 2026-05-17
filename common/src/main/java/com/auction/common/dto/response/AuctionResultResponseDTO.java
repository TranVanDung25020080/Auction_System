package com.auction.common.dto.response;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ReponseType;

public class AuctionResultResponseDTO extends BaseResponse{
    private int auctionId;
    private int winnerId;
    private double finalPrice;
    private AuctionStatus status;

    public AuctionResultResponseDTO(int auctionId, int winnerId, double finalPrice, AuctionStatus status) {
        this.auctionId = auctionId;
        this.winnerId = winnerId;
        this.finalPrice = finalPrice;
        this.status = status;
        this.responseType= ReponseType.AUCTION_RESULT;
    }
    public AuctionResultResponseDTO(int winnerId){
        this.winnerId=winnerId;
        this.responseType= ReponseType.AUCTION_RESULT;
    }

    //Getter
    //region
    public int getAuctionId() { return auctionId; }
    public int getWinnerName() { return winnerId; }
    public double getFinalPrice() { return finalPrice; }
    public AuctionStatus getStatus() { return status; }

    @Override
    public String displayMessage() {
        return "User "+winnerId+" has won";
    }
    //endregion
    //method for other classes to call:
    public void setStatus(AuctionStatus auctionStatus){
        this.status=auctionStatus;
    }
}
