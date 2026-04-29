package com.auction.common.dto.response;

import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;

public class UserResponseDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private UserRole userRole;
    private double balance;
    //Construtor
    public UserResponseDTO(String userId, String userName, String userEmail, UserRole userRole, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.balance = balance;
    }
    public UserResponseDTO(User user){
        this.userId=user.getUserId();
        this.userName=user.getUserName();
        this.userEmail=user.getEmail();
        this.userRole=user.getUserRole();
        this.balance=user.getBalance();
    }

    //Getter
    //region
    public String getUserId() {return userId;}
    public String getUserName() {return userName;}
    public String getUserEmail() {return userEmail;}
    public UserRole getUserRole() {return userRole;}
    public double getBalance() {return balance;}
    //endregion
}
