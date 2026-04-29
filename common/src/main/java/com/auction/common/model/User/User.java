package com.auction.common.model.User;

import com.auction.common.enums.UserRole;
import com.auction.common.model.Entity;

import java.io.Serializable;

public abstract class User implements Entity, Serializable {
    protected String userId;
    protected String ownerName;
    protected String userName;
    protected String password;
    protected String email;
    protected UserRole userRole;
    protected double balance;

    //Constructor
    public User(String userId, String userName, String email,double balance) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.balance=balance;
    }
    public User(String userId, String userName, String email){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
    public User(String ownerName,String userName,String password,UserRole userRole){
        this.ownerName=ownerName;
        this.userName=userName;
        this.password=password;
        this.userRole=userRole;
    }

    //Getter
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public UserRole getUserRole(){
        return this.userRole;
    }
    public double getBalance(){
        return this.balance;
    }


    @Override
    public String getId() { return userId; }
    public void printInfo() {}

}
