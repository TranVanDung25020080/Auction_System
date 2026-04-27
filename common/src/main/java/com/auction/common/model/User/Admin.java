package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Admin extends User {

    private String role_level;

    public Admin(String userId, String username, String email, String password, UserRole role) {
        super(userId, username, email, password, role);
        this.role_level = role_level;
    }

    public String getRoleLevel() {
        return role_level;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Admin Role Level: " + role_level);
    }
}