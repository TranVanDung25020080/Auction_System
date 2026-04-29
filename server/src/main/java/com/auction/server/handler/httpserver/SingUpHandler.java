package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.UserResponseDTO;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auth.SignUpService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

public class SingUpHandler extends HttpBaseHandler {
    /*@Override
    public void handle(HttpExchange exchange){
        String request=super.getResponse(exchange);

        JsonObject jsonObject= JsonParser.parseString(request).getAsJsonObject();




        UserResponseDTO userResponseDTO=new SignUpService().singup();



    }*/
}
