package com.auction.common.model.User;

public class Seller extends User {
    private String shopName;
    private double rating;
    private double balance;

    public Seller(String userId, String userName, String email, String shopName, double rating, double balance) {
        super(userId, userName, email);
        this.shopName = shopName;
        this.rating = rating;
        this.balance = balance;
    }

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
