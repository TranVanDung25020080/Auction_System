package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Bidder extends User {

    //Constructor
    public Bidder(int userId, String ownerName, String userName, double balance) {
        super(userId ,ownerName ,userName, balance);
        this.userRole= UserRole.BIDDER;
    }
    public Bidder(String ownerName, String userName, String password){
        super(ownerName,userName,password,UserRole.BIDDER);
    }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public void printInfo() {
        System.out.println("[BIDDER] Id: " + getUserId() + "Name: " + getOwnerName() + "Balance " +balance);
    }

    // moi
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
