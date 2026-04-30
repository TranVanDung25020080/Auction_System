package com.auction.common.model.User;

import com.auction.common.enums.UserRole;
import com.auction.common.model.Entity;

import java.io.Serializable;

public abstract class User implements Entity, Serializable {
    protected int userId;
    protected String ownerName;
    protected String userName;
    protected String password;
    protected String email;
    protected UserRole userRole;
    protected double balance;

    //Constructor
    public User(int userId, String ownerName, String userName, double balance) {
        this.userId = userId;
        this.ownerName = ownerName;
        this.userName = userName;
        this.balance=balance;
    }
    public User(int userId, String ownerName, String userName){
        this.userId = userId;
        this.userName = ownerName;
        this.email = userName;
    }
    public User(String ownerName,String userName,String password,UserRole userRole){
        this.ownerName=ownerName;
        this.userName=userName;
        this.password=password;
        this.userRole=userRole;
    }

    //Getter
    public int getUserId() { return userId; }
    public String getOwnerName() { return ownerName; }
    public String getUserName() { return userName; }
    public UserRole getUserRole(){
        return this.userRole;
    }
    public double getBalance(){
        return this.balance;
    }


    @Override
    public int getId() { return userId; }
    public void printInfo() {}

}
