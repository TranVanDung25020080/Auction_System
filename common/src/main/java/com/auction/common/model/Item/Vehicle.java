package com.auction.common.model.Item;

import com.auction.common.enums.ItemType;
import com.auction.common.model.User.Seller;

public class Vehicle extends Item {
    private String company;

    public Vehicle() {
        super(ItemType.VEHICLE);
    }

    public Vehicle(String id, String name, String description, double initialPrice, Seller seller, String company) {
        super(id, name, description, initialPrice, ItemType.VEHICLE, seller);
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    @Override
    public void printInfo() {
        System.out.println("[Vehicle] Name: " + getName() + ", SellerID: " + getSeller().getUserId() + ", Initial Price: " + getInitialPrice() + ", Company: " + company);
    }
}
