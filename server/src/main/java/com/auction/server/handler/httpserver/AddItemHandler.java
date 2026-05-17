package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.AddItemResponseDTO;
import com.auction.common.model.Item.Item;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.item.ItemService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class AddItemHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        String itemRequest=super.getRequest(exchange);

        Item itemObject=gson.fromJson(itemRequest, Item.class);

        AddItemResponseDTO addItemResponseDTO=new AddItemResponseDTO();

        try {
            addItemResponseDTO=new ItemService().addItem(itemObject);
        addItemResponseDTO.setMessage("Add item successfully!");
        } catch (Exception e) {
            addItemResponseDTO.setMessage(e.getMessage());
        }

        this.response=gson.toJson(addItemResponseDTO);
        super.handle(exchange);


    }
}
