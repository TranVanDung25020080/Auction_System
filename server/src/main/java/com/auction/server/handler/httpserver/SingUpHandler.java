package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.UserResponseDTO;
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
            String request=super.getResponse(exchange);

            JsonObject jsonObject= JsonParser.parseString(request).getAsJsonObject();

            String ownerName=jsonObject.get("ownerName").getAsString();
            String userName=jsonObject.get("userName").getAsString();
            String password=jsonObject.get("password").getAsString();
            String role=jsonObject.get("role").getAsString();


            userResponseDTO=new SignUpService().signUp(ownerName,userName,password,role);
        } catch (DatabaseException e) {
            userResponseDTO.setMessage(e.getMessage());
        }

        this.response=new Gson().toJson(userResponseDTO);
        super.handle(exchange);


    }
}
