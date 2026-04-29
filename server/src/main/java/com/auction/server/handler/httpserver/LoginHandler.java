package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auth.LoginService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import javax.naming.AuthenticationException;
import java.io.IOException;

public class LoginHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            this.response=super.getResponse(exchange);

            JsonObject jsonObject=JsonParser.parseString(response).getAsJsonObject();

            String userName=jsonObject.get("userName").getAsString();
            String password=jsonObject.get("password").getAsString();

            UserResponseDTO userResponseDTO=new LoginService().login(userName,password);

            this.response=new Gson().toJson(userResponseDTO);


        } catch (AuthenticationException e) {
            this.response=e.getMessage();
        } catch (DatabaseException e) {
            this.response=e.getMessage();
        }

        super.handle(exchange);

    }


}
