package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Seller extends User {
    private String shop_name;
    private double rating;
    public Seller(String userId, String username, String email, String password, UserRole role, String shopName, double rating) {
        super(userId,username,email,password,role);
        this.shop_name = shopName;
        this.rating = rating;
    }
    public String getShopName() { return shop_name; }
    public double getRating() { return rating; }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Shop Name: " + shop_name + " - Rating: " + rating + "⭐");
    }
}
