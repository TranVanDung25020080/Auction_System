package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.User.Seller;

import static com.auction.common.enums.ItemStatus.AVAILABLE;

public class Art extends Item {
    private String author;
    //Constructor
    public Art(String id, String name, String description, double initialPrice, Seller seller, ItemStatus itemStatus, String author) {
        super(id, name, description, initialPrice, seller, itemStatus);
        this.author = author;
    }
    public Art(String id, String name, String description, double initialPrice, ItemStatus itemStatus, String author){
        this.id=id;
        this.name=name;
        this.description=description;
        this.initialPrice=initialPrice;
        this.item_status=itemStatus;
        this.author=author;

    }
    //

    public String getAuthor() {
        return author;
    }

    @Override
    public void printInfo() {
        System.out.println("[Art] Name: " + getName() + ", SellerID: " + ", Initial Price: " + getInitialPrice() + ", Author: " + author + ", Item_status: " + getItem_status() );
    }


    //test
    static void main(String[] args) {
        Art art=new Art("01","art1","hello",100,AVAILABLE,"khanh");

        art.printInfo();
    }
}
