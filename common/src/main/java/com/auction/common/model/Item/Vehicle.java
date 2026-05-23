package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.enums.ItemType;
import com.auction.common.model.User.Seller;

public class Vehicle extends Item {
    private String company;

    //Constructor
    public Vehicle(int id, String name, String description, double initialPrice, Seller seller, ItemStatus itemStatus, String company) {
        super(id, name, description, initialPrice, seller,itemStatus);
        this.company = company;
        this.itemType= ItemType.VEHICLE;
    }
    public Vehicle( String name, String description, double initialPrice, Seller seller, ItemStatus itemStatus, String company) {
        super(name, description, initialPrice, seller,itemStatus);
        this.company = company;
        this.itemType= ItemType.VEHICLE;
    }
    public Vehicle( String name, String description, double initialPrice, int sellerId, ItemStatus itemStatus) {
        super(name, description, initialPrice, sellerId,itemStatus);
        this.itemType= ItemType.VEHICLE;
    }

    public String getCompany() {
        return this.company;
    }

    @Override
    public void printInfo() {
        System.out.println("[Vehicle] Name: " + getName() + ", SellerID: " + this.sellerId + ", Initial Price: " + getInitialPrice() + ", Company: " + company + ", Item_status: " + getItem_status());
    }
}
