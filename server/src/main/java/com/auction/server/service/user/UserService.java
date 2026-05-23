package com.auction.server.service.user;

import com.auction.common.dto.request.DepositBalanceRequestDTO;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.DepositBalanceResponseDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.model.Auction.BidTransaction;
import com.auction.server.dao.BidDAO;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    public DepositBalanceResponseDTO depositBalance(DepositBalanceRequestDTO depositBalanceRequestDTO){
        DepositBalanceResponseDTO depositBalanceResponseDTO=new DepositBalanceResponseDTO();

        int userId= depositBalanceRequestDTO.getUserId();
        double amount=depositBalanceRequestDTO.getAmount();
        double balance=depositBalanceRequestDTO.getBalance();

        try{
            new UserDAO().updateBalance(userId,amount+balance);
            depositBalanceResponseDTO.setUserId(userId);
            depositBalanceResponseDTO.setCurrentBalance(balance+amount);
            depositBalanceResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
            depositBalanceResponseDTO.setMessage("deposit balance successfully!");
        } catch (DatabaseException e) {
            depositBalanceResponseDTO.setUserId(userId);
            depositBalanceResponseDTO.setCurrentBalance(balance);
            depositBalanceResponseDTO.setAuthStatus(AuthStatus.FAILED);
            depositBalanceResponseDTO.setMessage(e.getMessage());
        }

        return  depositBalanceResponseDTO;

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



}
