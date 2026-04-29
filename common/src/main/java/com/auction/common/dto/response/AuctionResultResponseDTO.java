package com.auction.common.dto.response;

import com.auction.common.enums.AuctionStatus;

public class AuctionResultResponseDTO {
    private String auctionId;
    private String winnerName;
    private double finalPrice;
    private AuctionStatus status;

    public AuctionResultResponseDTO(String auctionId, String winnerName, double finalPrice, AuctionStatus status) {
        this.auctionId = auctionId;
        this.winnerName = winnerName;
        this.finalPrice = finalPrice;
        this.status = status;
    }

    //Getter
    //region
    public String getAuctionId() { return auctionId; }
    public String getWinnerName() { return winnerName; }
    public double getFinalPrice() { return finalPrice; }
    public AuctionStatus getStatus() { return status; }
    //endregion
}
