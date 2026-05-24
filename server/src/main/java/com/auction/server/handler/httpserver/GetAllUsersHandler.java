package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.user.UserService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetAllUsersHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        UserResponseDTO userResponseDTO=new UserService().getAllUsers();

        this.response=gson.toJson(userResponseDTO);

        super.handle(exchange);



    }
}
