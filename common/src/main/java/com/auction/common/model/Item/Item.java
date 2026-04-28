package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Entity;
import com.auction.common.model.User.Seller;

import java.io.Serializable;

public abstract class Item implements Entity, Serializable {
    private String id;
    private String name;
    private String description;
    private double initialPrice;
    private Seller seller;
    private ItemStatus item_status;

    public Item(String id, String name, String description, double InitialPrice, Seller seller, ItemStatus item_status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initialPrice = InitialPrice;
        this.seller = seller;
        this.item_status = item_status;
    }

    //region
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getInitialPrice() { return initialPrice; }
    public Seller getSeller() { return seller; }
    public ItemStatus getItem_status() { return item_status; }
    //endregion

    @Override
    public String getId() { return id; }
    public void printInfo() {}
}
