package com.auction.server.handler.httpserver;

import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.enums.AuthStatus;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.item.ItemService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetItemHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        String jsonRequest=super.getRequest(exchange);
        System.out.println(jsonRequest);
        GetItemRequestDTO getItemRequestDTO=gson.fromJson(jsonRequest, GetItemRequestDTO.class);

        GetItemReponseDTO getItemReponseDTO=new GetItemReponseDTO();
        try{
            getItemReponseDTO=new ItemService().getItemBySellerId(getItemRequestDTO);
            getItemReponseDTO.setStatus(AuthStatus.SUCCESS);
        } catch (DatabaseException e) {
            e.printStackTrace();
            getItemReponseDTO.setStatus(AuthStatus.SERVER_ERROR);
            getItemReponseDTO.setMessage(e.getMessage());
        }
        this.response=gson.toJson(getItemReponseDTO);
        super.handle(exchange);

    }
}
