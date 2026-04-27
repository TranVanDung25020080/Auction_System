package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Bidder extends User {
    private double balance;

    public Bidder() {
        super(UserRole.BIDDER);
    }

    public Bidder(String userId, String username, String email, String password, double balance) {
        super(userId, username,email,password,UserRole.BIDDER);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Balance: $" + balance);
    }
}
