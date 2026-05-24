package com.auction.client.network.http;

import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.request.ItemRequestDTO;
import com.auction.common.dto.response.ItemResponseDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.enums.HttpMethod;
import com.auction.common.model.Item.Item;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLOutput;


public class ItemAPI {
    public GetItemReponseDTO getItemBySellerId(GetItemRequestDTO getItemRequestDTO) throws IOException {
        Gson gson=new Gson();
        String jsonRequest=gson.toJson(getItemRequestDTO);

/*        URL url=new URL("http://localhost:8000/getitem/sellerid");*/
        String route="/getitem/sellerid";
        HttpMethod method=HttpMethod.POST;

        String jsonReponse=BaseApi.getJsonReponse(jsonRequest,route,method);
        System.out.println(jsonReponse);

        return gson.fromJson(jsonReponse, GetItemReponseDTO.class);

    }
    public ItemResponseDTO addItem(Item item) throws IOException {
        Gson gson=new Gson();

        String jsonRequest=gson.toJson(item);
        String route="/additem";
        HttpMethod httpMethod=HttpMethod.POST;

        String jsonReponse=BaseApi.getJsonReponse(jsonRequest,route,httpMethod);

        return gson.fromJson(jsonReponse, ItemResponseDTO.class);

    }

    public ItemResponseDTO removeItem(ItemRequestDTO itemRequestDTO) throws IOException {
        Gson gson=new Gson();
        String route="/removeitem";

        String jsonRequest=gson.toJson(itemRequestDTO);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route,HttpMethod.POST);

        return gson.fromJson(jsonResponse, ItemResponseDTO.class);

    }
    //test
    static void main(String[] args) {
        GetItemRequestDTO getItemRequestDTO=new GetItemRequestDTO(2);

        try{
            GetItemReponseDTO getItemReponseDTO=new ItemAPI().getItemBySellerId(getItemRequestDTO);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
