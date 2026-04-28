package com.auction.common.model.User;

import com.auction.common.model.Entity;

import java.io.Serializable;

public abstract class User implements Entity, Serializable {
    private String userId;
    private String userName;
    private String email;

    public User(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    //region
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    //endregion


    @Override
    public String getId() { return userId; }
    public void printInfo() {}
}
