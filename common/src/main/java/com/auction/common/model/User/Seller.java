package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Seller extends User {
    private double rating;

    // Constructor
    public Seller(int userId, String ownerName, String userName, String password, double rating, double balance) {
        super(userId, ownerName, userName, password, balance);
        this.rating = rating;
        this.userRole = UserRole.SELLER;
    }
    //Cons
    public Seller(int userId, String ownerName, String userName, double rating, double balance) {
        super(userId, ownerName, userName, "", balance);
        this.rating = rating;
        this.userRole = UserRole.SELLER;
    }

    // Getter
    public double getRating() {
        return rating;
    }

    @Override
    public void printInfo() {
        System.out.println("[SELLER] Id: " + getUserId() + " Name: " + getOwnerName() + " Rating: " + rating);
    }
}