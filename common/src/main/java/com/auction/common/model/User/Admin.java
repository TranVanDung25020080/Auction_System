package com.auction.common.model.User;

public class Admin extends User {
    private String roleLevel;

    public Admin(String userId, String userName, String email, String roleLevel) {
        super(userId, userName, email);
        this.roleLevel = roleLevel;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    @Override
    public void printInfo() {
        System.out.println("[ADMIN] Id: " + getUserId() + "Name: " + getUserName() + "RoleLevel: " + roleLevel);
    }
}
