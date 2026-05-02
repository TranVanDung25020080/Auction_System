package com.auction.common.model.User;

import com.auction.common.enums.UserRole;

public class Admin extends User {
    private String roleLevel;

    public Admin(int userId, String ownerName, String userName, double balance) {
        super(userId, ownerName, userName, balance);
        this.roleLevel = "AUCTIONEER";
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
