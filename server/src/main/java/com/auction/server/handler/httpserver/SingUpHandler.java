package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.common.enums.UserRole;
import com.auction.common.model.User.User;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auth.SignUpService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class SingUpHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UserResponseDTO userResponseDTO=null;
        try{
            String request=super.getRequest(exchange);

            JsonObject jsonObject= JsonParser.parseString(request).getAsJsonObject();

            String ownerName=jsonObject.get("ownerName").getAsString();
            String userName=jsonObject.get("userName").getAsString();
            String password=jsonObject.get("password").getAsString();
            String role=jsonObject.get("role").getAsString();


            userResponseDTO=new SignUpService().signUp(ownerName,userName,password,UserRole.valueOf(role));
            userResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
            userResponseDTO.setMessage("register successfully");
        } catch (DatabaseException e) {
            userResponseDTO.setAuthStatus(AuthStatus.INVALID_CREDENTIALS);
            userResponseDTO.setMessage(e.getMessage());
        }

        this.response=new Gson().toJson(userResponseDTO);
        super.handle(exchange);


    }
}
