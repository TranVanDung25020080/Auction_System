package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Admin extends User {

    private String role_level;

    public Admin() {
        super(UserRole.ADMIN);
    }

    public Admin(String userId, String username, String email, String password,String role_level) {
        super(userId, username, email, password, UserRole.ADMIN);
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