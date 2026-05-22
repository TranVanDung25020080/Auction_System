package com.auction.server.handler.httpserver;

import com.auction.common.dto.request.DepositBalanceRequestDTO;
import com.auction.common.dto.response.DepositBalanceResponseDTO;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.user.UserService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class DepositBalanceHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        String jsonResponse=super.getRequest(exchange);
        System.out.println(jsonResponse);

        DepositBalanceRequestDTO depositBalanceRequestDTO=gson.fromJson(jsonResponse, DepositBalanceRequestDTO.class);

        DepositBalanceResponseDTO depositBalanceResponseDTO=new UserService().depositBalance(depositBalanceRequestDTO);

        this.response=gson.toJson(depositBalanceResponseDTO);
        super.handle(exchange);


    }
}
