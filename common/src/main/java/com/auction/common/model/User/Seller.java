package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Seller extends User {
    private String shopName;
    private double rating;

    //Constructor
    public Seller(int userId, String ownerName, String userName, String shopName, double rating, double balance) {
        super(userId, ownerName, userName, balance);
        this.shopName = shopName;
        this.rating = rating;
        this.userRole= UserRole.SELLER;
    }
    public Seller(String ownerName, String userName, String password){
        super(ownerName,userName,password,UserRole.SELLER);
    }

    //getter

    public String getShopName() {
        return shopName;
    }
    public double getRating() {
        return rating;
    }
    public double getBalance() {return balance;}

    @Override
    public void printInfo() {
        System.out.println("[SELLER] Id: " + getUserId() + "Name: " + getOwnerName() + "ShopName " + shopName + "Rating: " + rating);
    }
}
