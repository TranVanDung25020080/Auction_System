package com.auction.common.dto.response;

import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;

public class UserResponseDTO {
    private int userId;
    private String ownerName;
    private String userName;
    private UserRole userRole;
    private double balance;
    private AuthStatus authStatus;
    private String message;


    //Construtor
    public UserResponseDTO(int userId, String ownerName, String userName, UserRole userRole, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.ownerName = ownerName;
        this.userRole = userRole;
        this.balance = balance;
    }
    public UserResponseDTO(User user){
        this.userId = user.getUserId();
        this.userName=user.getUserName();
        this.ownerName=user.getOwnerName();
        this.userRole=user.getUserRole();
        this.balance=user.getBalance();
    }
    public UserResponseDTO(){}

    //Getter
    public int getUserId() {return userId;}
    public String getUserName() {return userName;}
    public String getOwnerName() {return ownerName;}
    public UserRole getUserRole() {return userRole;}
    public double getBalance() {return balance;}
    //Setter
    public void setAuthStatus(AuthStatus authStatus){
        this.authStatus=authStatus;
    }
    public void setMessage(String message){
        this.message=message;
    }

}
