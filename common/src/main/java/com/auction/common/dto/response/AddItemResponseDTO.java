package com.auction.common.dto.response;

import com.auction.common.model.Item.Item;

public class AddItemResponseDTO {
    private Item item;
    private String message;
    //
    public AddItemResponseDTO(){}
    public AddItemResponseDTO(Item item,String message){
        this.item=item;
        this.message=message;
    }
    //getter
    public Item getItem(){
        return this.item;
    }
    public String getMessage(){
        return this.message;
    }

    //setter
    public void setItem(Item item){
        this.item=item;
    }
    public void setMessage(String message){
        this.message=message;
    }

}
