package com.auction.common.model.Item;

import com.auction.common.enums.ItemType;
import com.auction.common.model.User.Seller;

public class Electronics extends Item {
    private int warranty;

    public Electronics() {
        super(ItemType.ELECTRONICS);
    }

    public Electronics(String id, String name, String description, double initialPrice, Seller seller, int warranty) {
        super(id,name,description,initialPrice,ItemType.ELECTRONICS,seller);
        this.warranty = warranty;
    }

    public int getWarranty() {
        return warranty;
    }

    @Override
    public void printInfo() {
        System.out.println("[Electronic] Name: " + getName() + ", SellerID: " + getSeller().getUserId() + ", Initial Price: " + getInitialPrice() + ", Warranty: " + warranty + "month");
    }
}
