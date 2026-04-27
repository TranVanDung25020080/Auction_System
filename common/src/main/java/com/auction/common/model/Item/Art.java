package com.auction.common.model.Item;

import com.auction.common.enums.ItemType;
import com.auction.common.model.User.Seller;

public class Art extends Item {
    private String author;

    public Art() {
        super(ItemType.ART);
    }

    public Art(String id, String name, String description, double initialPrice, Seller seller, String author) {
        super(id, name, description, initialPrice, ItemType.ART, seller);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void printInfo() {
        System.out.println("[Art] Name: " + getName() + ", SellerID: " + getSeller().getUserId() + ", Initial Price: " + getInitialPrice() + ", Author: " + author);
    }
}
