package com.auction.server.service.user;

import com.auction.common.dto.request.DepositBalanceRequestDTO;
import com.auction.common.dto.response.DepositBalanceResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;

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


}
