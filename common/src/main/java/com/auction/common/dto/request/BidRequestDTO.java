package com.auction.common.dto.request;

public class BidRequestDTO {
    private int auctionId;
    private String bidAmount;


    public BidRequestDTO(int auctionId, String bidAmount) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
    }

    //Getter
    //region
    public int getAuctionId() {return auctionId;}
    public String getBidAmount() {return bidAmount;}
    //endregion
}
