package com.auction.common.dto.request;

public class GetItemRequestDTO {
    private int sellerId;
    private String requested_at;

    public GetItemRequestDTO(int sellerId){
        this.sellerId=sellerId;
    }
    //getter
    public int getSellerId(){
        return this.sellerId;
    }
    public String getRequested_at(){
        return this.requested_at;
    }
    //setter
    public void setRequested_at(String requestedAt){
        this.requested_at=requestedAt;
    }
    public void setSellerId(){
        this.sellerId=sellerId;
    }
}
