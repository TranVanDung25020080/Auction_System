package com.auction.common.model.User;

import com.auction.common.enums.UserRole;
import com.auction.common.model.Entity;

public class User implements Entity {
    private String userId;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private String role_level;
    private String shop_name;
    private double rating;
    private double balance;

    public User(String userId, String username, String email, String password, UserRole role, String role_level, String shop_name, double rating, double balance) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.role_level = role_level;
        this.shop_name = shop_name;
        this.rating = rating;
        this.balance = balance;
    }

    //Getter
    //region
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getRole_level() {
        return role_level;
    }

    public String getShop_name() {
        return shop_name;
    }

    public double getRating() {
        return rating;
    }

    public double getBalance() {
        return balance;
    }
    //endregion

    @Override
    public void getId() {
        return userId;
    }
    @Override
    public getInfo() {

    }
}




