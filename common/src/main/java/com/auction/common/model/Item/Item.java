package com.auction.common.model.Item;

import com.auction.common.enums.ItemType;
import com.auction.common.model.Entity;
import com.auction.common.model.User.Seller;

public abstract class Item implements Entity {
    private String id;
    private String name;
    private String description;
    private double initialPrice;
    private Seller seller;
    private ItemType item_type;

    public Item() {}

    public Item(ItemType item_type) {
        this.item_type = item_type;
    }

    public Item (String id, String name, String description, double InitialPrice,ItemType item_type, Seller seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initialPrice = InitialPrice;
        this.item_type = item_type;
        this.seller = seller;
    }

    //region
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getInitialPrice() { return initialPrice; }
    public ItemType getItemType() { return item_type; }
    public Seller getSeller() { return seller; }
    //endregion

    @Override
    public String getId() { return id; }
    public void printInfo() {}

}
