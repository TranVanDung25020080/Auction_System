package com.auction.common.model.User;

public class Bidder extends User {
    private double balance;

    public Bidder(String userId, String username, String email, double balance) {
        super(userId, username, email);
        this.balance = balance;
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
