package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.User.Seller;

public class Electronics extends Item {
    private int warranty;

    public Electronics(String id, String name, String description, double initialPrice, Seller seller, ItemStatus itemStatus, int warranty) {
        super(id,name, description, initialPrice, seller, itemStatus);
        this.warranty = warranty;
    }

    public int getWarranty() {
        return warranty;
    }

    @Override
    public void printInfo() {
        System.out.println("[Electronic] Name: " + getName() + ", SellerID: " + getSeller().getUserId() + ", Initial Price: " + getInitialPrice() + ", Warranty: " + warranty + "month" + ", Item_status: " + getItem_status());
    }
}
