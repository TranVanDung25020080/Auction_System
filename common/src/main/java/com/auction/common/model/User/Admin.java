package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Admin extends User {
    private String roleLevel;

    public Admin(int userId, String ownerName, String email, String roleLevel) {
        super(userId, ownerName, email);
        this.roleLevel = roleLevel;
        this.userRole= UserRole.ADMIN;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    @Override
    public void printInfo() {
        System.out.println("[ADMIN] Id: " + getUserId() + "Name: " + getOwnerName() + "RoleLevel: " + roleLevel);
    }
}
