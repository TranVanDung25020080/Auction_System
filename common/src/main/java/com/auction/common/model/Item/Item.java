package com.auction.common.model.Item;

import com.auction.common.enums.ItemStatus;
import com.auction.common.enums.ItemType;
import com.auction.common.model.Entity;
import com.auction.common.model.User.Seller;

import java.io.Serializable;

public class Item implements Entity, Serializable {
    protected int id,sellerId;
    protected String name;
    protected String description;
    protected double initialPrice;
    protected Seller seller;
    protected ItemStatus item_status;
    protected ItemType itemType;
    //Constructor
    public Item(int id, String name, String description, double InitialPrice, Seller seller, ItemStatus item_status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initialPrice = InitialPrice;
        this.seller = seller;
        this.item_status = item_status;
    }
    public Item(String name, String description, double InitialPrice, Seller seller, ItemStatus item_status) {
        this.name = name;
        this.description = description;
        this.initialPrice = InitialPrice;
        this.seller = seller;
        this.item_status = item_status;
    }
    public Item(String name, String description, double InitialPrice, int sellerId, ItemStatus item_status) {
        this.name = name;
        this.description = description;
        this.initialPrice = InitialPrice;
        this.sellerId=sellerId;
        this.item_status = item_status;
    }
    public Item(){};
    //

    //region
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getInitialPrice() { return initialPrice; }
    public Seller getSeller() { return seller; }
    public ItemStatus getItem_status() { return item_status; }
    public int getSellerId(){return this.sellerId;}
    public ItemType getItemType(){return this.itemType;}
    //endregion

    //setter
    public void setItem_status(ItemStatus itemStatus){
        this.item_status=itemStatus;
    }
    public void setSellerId(int sellerId){
        this.sellerId=sellerId;
    }

    @Override
    public int getId() { return id; }
    public void printInfo() {}
}
