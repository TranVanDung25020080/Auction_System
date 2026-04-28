package com.auction.common.dto.response;

import com.auction.common.enums.ItemStatus;

public class AuctionItemResponseDTO {
    private String auctionId;
    private String itemName;
    private double currentPrice;
    private ItemStatus item_status;

    public AuctionItemResponseDTO(String auctionId, String itemName, double currentPrice, ItemStatus item_status) {
        this.auctionId = auctionId;
        this.itemName = itemName;
        this.currentPrice = currentPrice;
        this.item_status = item_status;
    }

    //Getter
    //region
    public String getAuctionId() {return auctionId;}
    public void setAuctionId(String auctionId) {this.auctionId = auctionId;}
    public String getItemName() {return itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    //endregion
}
