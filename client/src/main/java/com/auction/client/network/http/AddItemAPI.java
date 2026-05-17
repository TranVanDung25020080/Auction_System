package com.auction.client.network.http;

import com.auction.common.dto.response.AddItemResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.auction.common.model.Item.Item;
import com.google.gson.Gson;

import java.io.IOException;

public class AddItemAPI {
    public AddItemResponseDTO addItem(Item item) throws IOException {
        Gson gson=new Gson();

        String jsonRequest=gson.toJson(item);
        String route="/additem";
        HttpMethod httpMethod=HttpMethod.POST;

        String jsonReponse=BaseApi.getJsonReponse(jsonRequest,route,httpMethod);

        return gson.fromJson(jsonReponse, AddItemResponseDTO.class);


    }
}
