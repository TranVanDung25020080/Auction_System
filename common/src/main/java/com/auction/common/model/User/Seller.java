package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Seller extends User {
    private double rating;

    //Constructor
    public Seller(int userId, String ownerName, String userName, double rating, double balance) {
        super(userId, ownerName, userName, balance);
        this.rating = rating;
        this.userRole= UserRole.SELLER;
    }
    public Seller(String ownerName, String userName, String password){
        super(ownerName,userName,password,UserRole.SELLER);
    }

    //getter

    public double getRating() {
        return rating;
    }
    public double getBalance() {return balance;}

    @Override
    public void printInfo() {
        System.out.println("[SELLER] Id: " + getUserId() + "Name: " + getOwnerName() + "Rating: " + rating);
    }

    //moi
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
