package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Bidder extends User {
    private double balance;

    public Bidder(String userId, String username, String email, double balance) {
        super(userId, username, email,balance);
        this.userRole= UserRole.BIDDER;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposited " + amount + " to Bidder " + getUserName());
        }
    }
    public void withdraw(double amount) {
        if (amount > 0 || balance >= amount) {
            this.balance -= amount;
            System.out.println("Withdrawn " + amount + " from Bidder " + getUserName());
        }
        System.out.println("Withdrawn not successful");
    }

    @Override
    public void printInfo() {
        System.out.println("[BIDDER] Id: " + getUserId() + "Name: " + getUserName() + "Balance " +balance);
    }
}
