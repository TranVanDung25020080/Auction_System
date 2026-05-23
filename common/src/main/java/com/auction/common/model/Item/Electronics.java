package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.enums.ItemType;
import com.auction.common.model.User.Seller;

public class Electronics extends Item {
    private int warranty;

    public Electronics(int id, String name, String description, double initialPrice, Seller seller, ItemStatus itemStatus, int warranty) {
        super(id,name, description, initialPrice, seller, itemStatus);
        this.warranty = warranty;
        this.itemType= ItemType.ELECTRONICS;
    }
    public Electronics( String name, String description, double initialPrice, int sellerId, ItemStatus itemStatus) {
        super(name, description, initialPrice, sellerId,itemStatus);
        this.itemType= ItemType.ELECTRONICS;
    }

    public int getWarranty() {
        return warranty;
    }

    @Override
    public void printInfo() {
        System.out.println("[Electronic] Name: " + getName() + ", SellerID: " + this.sellerId + ", Initial Price: " + getInitialPrice() + ", Warranty: " + warranty + "month" + ", Item_status: " + getItem_status());
    }
}
