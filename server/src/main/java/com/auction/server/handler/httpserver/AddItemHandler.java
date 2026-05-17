package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.AddItemResponseDTO;
import com.auction.common.enums.ItemType;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.item.ItemService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class AddItemHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson=new Gson();

        String itemRequest=super.getRequest(exchange);
        System.out.println(itemRequest);

        JsonObject jsonObject = JsonParser.parseString(itemRequest).getAsJsonObject();
        ItemType itemType=ItemType.valueOf(jsonObject.get("itemType").getAsString());


        Item itemObject=null;
        if (itemType==ItemType.VEHICLE){
            itemObject=gson.fromJson(itemRequest, Vehicle.class);

        }
        else if (itemType==ItemType.ELECTRONICS){
            itemObject=gson.fromJson(itemRequest, Electronics.class);
        }
        else if (itemType==ItemType.ART){
            itemObject=gson.fromJson(itemRequest,Art.class);
        }



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
