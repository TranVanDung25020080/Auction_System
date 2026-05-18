package com.auction.client.network.http;

import com.auction.common.dto.request.GetAuctionRequestDTO;
import com.auction.common.dto.response.CreateAuctionResponseDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.auction.common.model.Auction.Auction;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class AuctionApi {
    public GetAuctionResponseDTO getAllAuction() throws IOException {
  /*      URL url=new URL("http://localhost:8000/getallauction");*/
        String route="/getallauction";

        HttpMethod httpMethod=HttpMethod.GET;

        String response=BaseApi.getJsonResponse(route,httpMethod);

        Gson gson = Converters.registerAll(new GsonBuilder()).create();

        return gson.fromJson(response, GetAuctionResponseDTO.class);

    }

    public CreateAuctionResponseDTO createAuction(Auction auction) throws IOException {
        String route="/createauction";

        Gson gson=Converters.registerAll(new GsonBuilder()).create();

        String jsonRequest=gson.toJson(auction);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route,HttpMethod.POST);

        return gson.fromJson(jsonResponse, CreateAuctionResponseDTO.class);

    }
    public GetAuctionResponseDTO getAuctionBySellerId(GetAuctionRequestDTO getAuctionRequestDTO) throws IOException {
        String route="/getauction/sellerid";

        String jsonRequest=new Gson().toJson(getAuctionRequestDTO);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route,HttpMethod.POST);

        Gson gson=Converters.registerAll(new GsonBuilder()).create();

        return gson.fromJson(jsonResponse,GetAuctionResponseDTO.class);

    }
}
