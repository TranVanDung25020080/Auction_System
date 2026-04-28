package com.auction.common.dto.request;

public class BidRequestDTO {
    private String auctionId;
    private String bidAmount;


    public BidRequestDTO(String auctionId, String bidAmount) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
    }

    //Getter
    //region
    public String getAuctionId() {return auctionId;}
    public String getBidAmount() {return bidAmount;}
    //endregion
}
