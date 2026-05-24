package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Bidder extends User {

    // Constructor
    public Bidder(int userId, String ownerName, String userName, String password, double balance) {
        super(userId, ownerName, userName, password, balance);
        this.userRole = UserRole.BIDDER;
    }

    // Constructor
    public Bidder(int userId, String ownerName, String userName, double balance) {
        super(userId, ownerName, userName, "", balance);
        this.userRole = UserRole.BIDDER;
    }

    @Override
    public void printInfo() {
        System.out.println("[BIDDER] Id: " + getUserId() + " Name: " + getOwnerName() + " Balance: " + getBalance());
    }
}