package com.auction.server.handler.httpserver;

import com.auction.common.dto.request.ItemRequestDTO;
import com.auction.common.dto.response.ItemResponseDTO;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.item.ItemService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class RemoveItemHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        String jsonRequest=super.getRequest(exchange);
        System.out.println(jsonRequest);

        ItemRequestDTO itemRequestDTO=gson.fromJson(jsonRequest, ItemRequestDTO.class);

        ItemResponseDTO itemResponseDTO=new ItemService().removeItem(itemRequestDTO);

        this.response=gson.toJson(itemResponseDTO);

        super.handle(exchange);

    }
}
