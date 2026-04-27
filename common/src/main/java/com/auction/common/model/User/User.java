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

    public User(String userId, String username, String email, String password, UserRole role,String role_level) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.role_level = role_level;
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

    //endregion

    @Override
    public String getId() {
        return userId;
    }
    @Override
    public void printInfo() {

    }
}




