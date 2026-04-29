package com.auction.common.dto.response;

public class BidUpdateResponseDTO {
    private String auctionId;
    private double newHighestPrice;
    private String highestBidderName;
    private String timeStamp;

    public BidUpdateResponseDTO(String auctionId, double newHighestPrice, String highestBidderName, String timeStamp) {
        this.auctionId = auctionId;
        this.newHighestPrice = newHighestPrice;
        this.highestBidderName = highestBidderName;
        this.timeStamp = timeStamp;
    }

    //Getter
    //region
    public String getAuctionId() { return auctionId; }
    public double getNewHighestPrice() { return newHighestPrice; }
    public String getHighestBidderName() { return highestBidderName; }
    public String getTimeStamp() { return timeStamp; }
    //endregion
}
