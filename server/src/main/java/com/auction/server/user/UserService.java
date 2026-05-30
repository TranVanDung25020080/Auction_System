package com.auction.server.service.user;

import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.model.Auction.BidTransaction;
import com.auction.common.model.User.User;
import com.auction.server.dao.BidDAO;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    public UserBalanceResponseDTO depositBalance(UserBalanceRequestDTO userBalanceRequestDTO){
        UserBalanceResponseDTO userBalanceResponseDTO =new UserBalanceResponseDTO();

        int userId= userBalanceRequestDTO.getUserId();
        double amount= userBalanceRequestDTO.getAmount();
        double balance= userBalanceRequestDTO.getBalance();

        try{
            new UserDAO().updateBalance(userId,amount+balance);
            userBalanceResponseDTO.setUserId(userId);
            userBalanceResponseDTO.setCurrentBalance(balance+amount);
            userBalanceResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
            userBalanceResponseDTO.setMessage("deposit balance successfully!");
        } catch (DatabaseException e) {
            userBalanceResponseDTO.setUserId(userId);
            userBalanceResponseDTO.setCurrentBalance(balance);
            userBalanceResponseDTO.setAuthStatus(AuthStatus.FAILED);
            userBalanceResponseDTO.setMessage(e.getMessage());
        }

        return userBalanceResponseDTO;
    }
    public UserBalanceResponseDTO withDraw(UserBalanceRequestDTO userBalanceRequestDTO){
        UserBalanceResponseDTO userBalanceResponseDTO=new UserBalanceResponseDTO();

        int userId= userBalanceRequestDTO.getUserId();
        double amount= userBalanceRequestDTO.getAmount();
        double balance= userBalanceRequestDTO.getBalance();

        try{

            if (amount<=balance){
                new UserDAO().updateBalance(userId,balance-amount);
                userBalanceResponseDTO.setUserId(userId);
                userBalanceResponseDTO.setCurrentBalance(balance-amount);
                userBalanceResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
                userBalanceResponseDTO.setMessage("withdraw successfully!");
            }
            else{
                userBalanceResponseDTO.setUserId(userId);
                userBalanceResponseDTO.setCurrentBalance(balance);
                userBalanceResponseDTO.setAuthStatus(AuthStatus.FAILED);
                userBalanceResponseDTO.setMessage("Khong du so du!");
            }
        } catch (DatabaseException e) {
            userBalanceResponseDTO.setUserId(userId);
            userBalanceResponseDTO.setCurrentBalance(balance);
            userBalanceResponseDTO.setAuthStatus(AuthStatus.FAILED);
            userBalanceResponseDTO.setMessage(e.getMessage());
        }

        return userBalanceResponseDTO;

    }


    public GetBidInfoResponseDTO getBidInfoByBidderId(GetBidInfoRequestDTO getBidInfoRequestDTO){
        GetBidInfoResponseDTO getBidInfoResponseDTO=new GetBidInfoResponseDTO();

        int bidderId=getBidInfoRequestDTO.getBidderId();
        int auctionId=getBidInfoRequestDTO.getAuctionId();

        try{
            List<BidTransaction> bidTransactionList=new BidDAO().getBidInfoByBidderId(bidderId,auctionId);

            getBidInfoResponseDTO.setBidTransactionList(bidTransactionList);
            getBidInfoResponseDTO.setMessage("Get bidInfo successfully!");
            getBidInfoResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
        } catch (SQLException e) {
            getBidInfoResponseDTO.setAuthStatus(AuthStatus.FAILED);
            getBidInfoResponseDTO.setMessage(e.getMessage());
        }

        return getBidInfoResponseDTO;
    }

    public GetBidInfoResponseDTO getBidInfoByAuctionId(GetBidInfoRequestDTO getBidInfoRequestDTO){
        GetBidInfoResponseDTO getBidInfoResponseDTO=new GetBidInfoResponseDTO();

        int auctionId=getBidInfoRequestDTO.getAuctionId();

        try{
            List<BidTransaction> bidTransactionList=new BidDAO().getBidsByAuctionId(auctionId);

            getBidInfoResponseDTO.setBidTransactionList(bidTransactionList);
            getBidInfoResponseDTO.setMessage("Get bidInfo successfully!");
            getBidInfoResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
        }
        catch (DatabaseException e) {
            getBidInfoResponseDTO.setAuthStatus(AuthStatus.FAILED);
            getBidInfoResponseDTO.setMessage(e.getMessage());
        }

        return getBidInfoResponseDTO;
    }
    public UserResponseDTO getAllUsers(){
        UserResponseDTO userResponseDTO=new UserResponseDTO();

        try{
            List<User> userList=new UserDAO().getAllUsers();

            userResponseDTO.setUserList(userList);
            userResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
            userResponseDTO.setMessage("get all user succesfully");
        } catch (SQLException e) {
            userResponseDTO.setMessage(e.getMessage());
            userResponseDTO.setAuthStatus(AuthStatus.FAILED);
        }
        return userResponseDTO;

    }
    public UserBalanceResponseDTO getUserBalanceById(UserBalanceRequestDTO userBalanceRequestDTO){
        int userId= userBalanceRequestDTO.getUserId();

        UserBalanceResponseDTO userBalanceResponseDTO=new UserBalanceResponseDTO();
        try {
            double currentBalance=new UserDAO().showBalance(userId);
            userBalanceResponseDTO.setCurrentBalance(currentBalance);
            userBalanceResponseDTO.setMessage("Get balance successfully");
            userBalanceResponseDTO.setAuthStatus(AuthStatus.SUCCESS);

        } catch (DatabaseException e) {
            userBalanceResponseDTO.setMessage(e.getMessage());
            userBalanceResponseDTO.setAuthStatus(AuthStatus.FAILED);
        }

        return userBalanceResponseDTO;

    }



}
