package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.server.exception.AuthException;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auth.LoginService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class LoginHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UserResponseDTO userResponseDTO=null;
        try {

            String request=super.getRequest(exchange);
            System.out.println(request);

            JsonObject jsonObject=JsonParser.parseString(request).getAsJsonObject();

            String userName=jsonObject.get("userName").getAsString();
            String password=jsonObject.get("password").getAsString();

            userResponseDTO=new LoginService().login(userName,password);

            userResponseDTO.setAuthStatus(AuthStatus.SUCCESS);
            userResponseDTO.setMessage("Login successfull!");


        } catch (AuthException e) {
            userResponseDTO.setAuthStatus(AuthStatus.INVALID_CREDENTIALS);
            userResponseDTO.setMessage(e.getMessage());

        } catch (DatabaseException e) {
            userResponseDTO.setAuthStatus(AuthStatus.SERVER_ERROR);
            userResponseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            userResponseDTO.setAuthStatus(AuthStatus.SERVER_ERROR);
            userResponseDTO.setMessage(e.getMessage());
        }

        this.response=new Gson().toJson(userResponseDTO);
        super.handle(exchange);

    }


}
