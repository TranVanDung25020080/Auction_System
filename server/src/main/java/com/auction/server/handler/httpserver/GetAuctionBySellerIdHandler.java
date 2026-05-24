package com.auction.server.handler.httpserver;

import com.auction.common.dto.request.GetAuctionRequestDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.auction.AuctionService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetAuctionBySellerIdHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String jsonRequest=super.getRequest(exchange);
        System.out.println(jsonRequest);

        GetAuctionRequestDTO getAuctionRequestDTO=new Gson().fromJson(jsonRequest, GetAuctionRequestDTO.class);
        GetAuctionResponseDTO getAuctionResponseDTO=new GetAuctionResponseDTO();

        try {
            getAuctionResponseDTO=new AuctionService().getAuctionBySellerId(getAuctionRequestDTO);
            getAuctionResponseDTO.setMessage("get auction by seller id successfully!");
        } catch (DatabaseException e) {
            getAuctionResponseDTO.setMessage(e.getMessage());
        }

        this.response= Converters.registerAll(new GsonBuilder()).create().toJson(getAuctionResponseDTO);

        super.handle(exchange);

    }
}
