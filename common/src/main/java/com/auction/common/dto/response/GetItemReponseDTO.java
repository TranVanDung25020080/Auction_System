package com.auction.common.dto.response;

import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.AuthStatus;
import com.auction.common.model.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class GetItemReponseDTO {
    private int sellerId;
    private List<Item> itemList;
    private AuthStatus status;
    private String message;
    //Constructor
    public GetItemReponseDTO(){}
    public GetItemReponseDTO(int sellerId,List<Item> itemList
                         ){
        this.sellerId=sellerId;
        this.itemList=itemList;
    }
    //getter
    public int getSellerId(){
        return this.sellerId;
    }
    public AuthStatus getStatus(){
        return this.status;
    }
    public String getMessage(){
        return this.message;
    }
    public List<Item> getItemList(){
        return this.itemList;
    }
    //setter
    public void setSellerId(int sellerId){
        this.sellerId=sellerId;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setItemList(List<Item> itemList){
        this.itemList=itemList;
    }
    public void setStatus(AuthStatus status){
        this.status=status;
    }
}
