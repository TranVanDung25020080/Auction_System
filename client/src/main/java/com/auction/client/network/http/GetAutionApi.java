package com.auction.client.network.http;

import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;

public class GetAutionApi {
    public GetAuctionResponseDTO getAllAuction() throws IOException {
        URL url=new URL("http://localhost:8000/getallauction");

        HttpMethod httpMethod=HttpMethod.GET;

        String response=BaseApi.getJsonResponse(url,httpMethod);

        Gson gson = Converters.registerAll(new GsonBuilder()).create();

        return gson.fromJson(response, GetAuctionResponseDTO.class);

    }
    /*//test
    static void main(String[] args) throws IOException {
        GetAuctionResponseDTO getAuctionResponseDTO= new GetAutionApi().getAllAuction();

        System.out.println(getAuctionResponseDTO.getAuctionList());
    }*/
}
