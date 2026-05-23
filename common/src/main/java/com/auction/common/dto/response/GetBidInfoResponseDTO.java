package com.auction.common.dto.response;

import com.auction.common.enums.AuthStatus;
import com.auction.common.model.Auction.BidTransaction;

import java.util.List;

public class GetBidInfoResponseDTO {
    private List<BidTransaction> bidTransactionList;
    private AuthStatus authStatus;
    private String message;
    //Constructor
    public GetBidInfoResponseDTO(){}

    public GetBidInfoResponseDTO(List<BidTransaction> bidTransactionList){
        this.bidTransactionList=bidTransactionList;
    }
    //getter
    public List<BidTransaction> getBidTransactionList(){
        return this.bidTransactionList;
    }
    public AuthStatus getAuthStatus(){
        return this.authStatus;
    }
    public String getMessage(){
        return this.message;
    }
    //setter
    public void setBidTransactionList(List<BidTransaction> bidTransactionList){
        this.bidTransactionList=bidTransactionList;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setAuthStatus(AuthStatus authStatus){
        this.authStatus=authStatus;
    }
}
