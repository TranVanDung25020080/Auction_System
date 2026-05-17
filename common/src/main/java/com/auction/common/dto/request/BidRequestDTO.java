package com.auction.common.dto.request;

import com.auction.common.enums.RequestType;

public class BidRequestDTO extends BaseRequestDTO{
    private int auctionId,bidderId;
    private double bidAmount;
    private double highCurrentPrice;


    public BidRequestDTO(int bidderId ,int auctionId, double bidAmount,double highCurrentPrice) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
        this.highCurrentPrice=highCurrentPrice;
        this.bidderId=bidderId;
        this.requestType= RequestType.NORMAL_BIDDING;
    }
    public BidRequestDTO(){
        this.requestType= RequestType.NORMAL_BIDDING;
    }

    //Getter
    //region
    public int getBidderId(){return this.bidderId;}
    public int getAuctionId() {return auctionId;}
    public double getBidAmount() {return bidAmount;}
    public double getHighCurrentPrice(){return this.highCurrentPrice;}
    //endregion
}
