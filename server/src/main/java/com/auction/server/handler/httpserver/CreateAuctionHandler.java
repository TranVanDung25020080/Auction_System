package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.CreateAuctionResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auction.AuctionRoomService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

public class CreateAuctionHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = Converters.registerAll(new GsonBuilder()).create();

        String jsonRequest=super.getRequest(exchange);
        System.out.println(jsonRequest);

        Auction auction=gson.fromJson(jsonRequest, Auction.class);

        CreateAuctionResponseDTO createAuctionResponseDTO=new CreateAuctionResponseDTO();

        try {
            createAuctionResponseDTO=new AuctionRoomService().createAuction(auction);
            createAuctionResponseDTO.setMessage("Create auction successfully!");
        } catch (DatabaseException e) {
            createAuctionResponseDTO.setMessage(e.getMessage());
        } catch (SQLException e) {
            createAuctionResponseDTO.setMessage(e.getMessage());
        }

        this.response=gson.toJson(createAuctionResponseDTO);
        super.handle(exchange);


    }
}
