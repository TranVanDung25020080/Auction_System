package com.auction.client.controller.sellerdashboard.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.client.network.http.UserApi;
import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.model.User.Seller;

import java.io.IOException;

public class ReloadButton {
    public void handle(SellerDashboardController sellerDashboardController){
        Seller seller=sellerDashboardController.getSeller();

        int userId=seller.getUserId();

        UserBalanceRequestDTO userBalanceRequestDTO=new UserBalanceRequestDTO();
        userBalanceRequestDTO.setUserId(userId);

        try{
            UserBalanceResponseDTO userBalanceResponseDTO=new UserApi().getBalanceByUserId(userBalanceRequestDTO);
            System.out.println(userBalanceResponseDTO.getMessage());

            double currentBalance=userBalanceResponseDTO.getCurrentBalance();
            seller.setBalance(currentBalance);

        } catch (IOException e) {
            Alert.showAlert("ERROR",e.getMessage());
        }

    }

}
