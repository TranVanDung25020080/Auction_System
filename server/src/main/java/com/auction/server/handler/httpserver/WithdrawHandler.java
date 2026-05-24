package com.auction.server.handler.httpserver;

import com.auction.common.dto.request.UserBalanceRequestDTO;
import com.auction.common.dto.response.UserBalanceResponseDTO;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.user.UserService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class WithdrawHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        String jsonRequest=super.getRequest(exchange);
        System.out.println(jsonRequest);

        UserBalanceRequestDTO userBalanceRequestDTO=gson.fromJson(jsonRequest, UserBalanceRequestDTO.class);

        UserBalanceResponseDTO userBalanceResponseDTO=new UserService().withDraw(userBalanceRequestDTO);

        this.response=gson.toJson(userBalanceResponseDTO);
        super.handle(exchange);


    }
}
