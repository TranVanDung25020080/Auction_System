package com.auction.common.dto.response;

import com.auction.common.enums.AuthStatus;

public class DepositBalanceResponseDTO {
    private int userId;
    private double currentBalance;
    private AuthStatus authStatus;
    private String message;
    //Constructor
    public DepositBalanceResponseDTO(int userId,double currentBalance){
        this.userId=userId;
        this.currentBalance=currentBalance;
    }
    public DepositBalanceResponseDTO(){};
    //getter
    public int getUserId(){
        return this.userId;
    }
    public double getCurrentBalance(){
        return this.currentBalance;
    }
    public AuthStatus getAuthStatus(){
        return this.authStatus;
    }
    public String getMessage(){
        return this.message;
    }
    //setter
    public void setUserId(int userId){
        this.userId=userId;
    }
    public void setCurrentBalance(double currentBalance){
        this.currentBalance=currentBalance;
    }
    public void setAuthStatus(AuthStatus authStatus){
        this.authStatus=authStatus;
    }
    public void setMessage(String message){
        this.message=message;
    }
}
