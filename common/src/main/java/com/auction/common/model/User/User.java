package com.auction.common.model.User;

import com.auction.common.enums.UserRole;
import com.auction.common.model.Entity;

public abstract class User implements Entity {
    private String userId;
    private String username;
    private String email;
    private String password;
    private UserRole role;

    public User() {}

    public User(UserRole role) {
        this.role = role;
    }

    public User(String userId, String username, String email, String password, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
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


    //endregion

    @Override
    public String getId() {
        return userId;
    }
    @Override
    public void printInfo() {

    }
}




