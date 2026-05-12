package com.auction.client.network.http;

import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.enums.HttpMethod;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class GetItemAPI {
    public GetItemReponseDTO getItemBySellerId(GetItemRequestDTO getItemRequestDTO) throws IOException {
        Gson gson=new Gson();
        String jsonRequest=gson.toJson(getItemRequestDTO);

        URL url=new URL("http://localhost:8000/getitem/sellerid");
        HttpMethod method=HttpMethod.POST;

        String jsonReponse=BaseApi.getJsonReponse(jsonRequest,url,method);

        return gson.fromJson(jsonReponse, GetItemReponseDTO.class);

    }
}
