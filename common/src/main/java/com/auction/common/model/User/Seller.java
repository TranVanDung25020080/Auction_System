package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Seller extends User {
    private String shopName;
    private double rating;
    //Constructor
    public Seller(String userId, String userName, String email, String shopName, double rating, double balance) {
        super(userId, userName, email,balance);
        this.shopName = shopName;
        this.rating = rating;
        this.userRole= UserRole.SELLER;
    }
    public Seller(String ownerName,String userName,String password){
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
        System.out.println("[SELLER] Id: " + getUserId() + "Name: " + getUserName() + "ShopName " + shopName + "Rating: " + rating);
    }
}
