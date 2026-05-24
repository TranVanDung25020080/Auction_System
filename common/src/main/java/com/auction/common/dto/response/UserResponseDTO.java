package com.auction.common.dto.response;

import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;

import java.util.List;

public class UserResponseDTO {
    private int userId;
    private String ownerName;
    private String userName;
    private UserRole userRole;
    private double balance;
    private AuthStatus authStatus;
    private String message;

    private List<User> userList;


    //Construtor
    public UserResponseDTO(int userId,  String ownerName, String userName, UserRole userRole, double balance) {
        this.userId = userId;
        this.ownerName = ownerName;
        this.userName = userName;
        this.userRole = userRole;
        this.balance = balance;
    }

    public UserResponseDTO(String ownerName, String userName, UserRole userRole) {
        this.userName = userName;
        this.ownerName = ownerName;
        this.userRole = userRole;
        this.balance = 0.0;
    }

    public UserResponseDTO() {}

    //Getter
    public int getUserId() {return userId;}
    public String getUserName() {return userName;}
    public String getOwnerName() {return ownerName;}
    public UserRole getUserRole() {return userRole;}
    public double getBalance() {return balance;}
    public String getMessage(){return this.message;}
    public AuthStatus getAuthStatus(){return this.authStatus;}
    public List<User> getUserList(){
        return this.userList;
    }
    //Setter
    public void setAuthStatus(AuthStatus authStatus){
        this.authStatus=authStatus;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setUserList(List<User> userList){
        this.userList=userList;
    }
}
