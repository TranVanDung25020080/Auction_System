package com.auction.common.dto.request;

public class DepositBalanceRequestDTO {
    private int userId;
    private double amount;
    private  double balance;
    //Constructor
    public DepositBalanceRequestDTO(int userId, double amount,double balance){
        this.userId=userId;
        this.amount=amount;
        this.balance=balance;
    }
    public DepositBalanceRequestDTO(){};
    //getter
    public int getUserId(){
        return this.userId;
    }
    public double getAmount() {
        return this.amount;
    }
    public double getBalance(){
        return this.balance;
    }
    //setter
    public void setUserId(int userId){
        this.userId=userId;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
    public void setBalance(double balance){
        this.balance=balance;
    }
}
