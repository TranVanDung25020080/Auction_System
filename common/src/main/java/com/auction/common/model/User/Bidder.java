package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Bidder extends User {

    //Constructor
    public Bidder(int userId, String username, String email, double balance) {
        super(userId, username, email,balance);
        this.userRole= UserRole.BIDDER;
    }
    public Bidder(String ownerName,String userName,String password){
        super(ownerName,userName,password,UserRole.BIDDER);
    }

    @Override
    public void printInfo() {
        System.out.println("[BIDDER] Id: " + getUserId() + "Name: " + getUserName() + "Balance " +balance);
    }
}
