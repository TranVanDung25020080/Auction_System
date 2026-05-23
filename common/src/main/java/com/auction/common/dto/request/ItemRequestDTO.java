package com.auction.common.dto.request;

import com.auction.common.enums.ItemType;

public class ItemRequestDTO {
    private String itemName;
    private String itemDescription;
    private double itemInitialPrice;
    private ItemType itemType;
    private int itemId;

    public ItemRequestDTO(String itemName, String itemDescription, double itemInitialPrice, ItemType itemType) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemInitialPrice = itemInitialPrice;
        this.itemType = itemType;
    }
    public ItemRequestDTO(int itemId){
        this.itemId=itemId;
    }


    //Getter
    //region
    public String getItemName() {return itemName;}
    public String getItemDescription() {return itemDescription;}
    public double getItemInitialPrice() {return itemInitialPrice;}
    public ItemType getItemType() {return itemType;}
    public int getItemId(){
        return this.itemId;
    }
    //endregion

}
