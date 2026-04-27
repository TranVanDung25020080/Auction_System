package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Admin extends User {

    private String adminCode;

    public Admin(String userId, String username, String email, String password, UserRole role, String roleLevel, String adminCode) {
        super(userId, username, email, password, role, roleLevel);
        this.adminCode = adminCode;
    }

    public String getAdminCode() {
        return adminCode;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Admin Access Code: " + adminCode);
    }
}