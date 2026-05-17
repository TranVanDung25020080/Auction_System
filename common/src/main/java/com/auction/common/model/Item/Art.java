package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.enums.ItemType;
import com.auction.common.model.User.Seller;

import static com.auction.common.enums.ItemStatus.AVAILABLE;

public class Art extends Item {
    private String author;

    //Constructor
    public Art(int id, String name, String description, double initialPrice, Seller seller, ItemStatus itemStatus, String author) {
        super(id, name, description, initialPrice, seller, itemStatus);
        this.author = author;
        this.itemType= ItemType.ART;
    }
    public Art( String name, String description, double initialPrice, int sellerId, ItemStatus itemStatus) {
        super(name, description, initialPrice, sellerId,itemStatus);
        this.itemType= ItemType.ELECTRONICS;
    }
    public Art(int id, String name, String description, double initialPrice, ItemStatus itemStatus, String author){
        this.id=id;
        this.name=name;
        this.description=description;
        this.initialPrice=initialPrice;
        this.item_status=itemStatus;
        this.author=author;
        this.itemType= ItemType.ELECTRONICS;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void printInfo() {
        System.out.println("[Art] Name: " + getName() + ", SellerID: " +this.sellerId+ ", Initial Price: " + getInitialPrice() + ", Author: " + author + ", Item_status: " + getItem_status() );
    }

}
