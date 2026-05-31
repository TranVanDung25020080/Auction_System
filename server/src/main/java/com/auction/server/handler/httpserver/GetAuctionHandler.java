package com.auction.server.handler.httpserver;

import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auction.AuctionService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetAuctionHandler extends HttpBaseHandler {
    public GetAuctionHandler(){}
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        GetAuctionResponseDTO getAuctionResponseDto=new GetAuctionResponseDTO();

        try{
            List<Auction> auctionList=new AuctionService().getAllAuctions();

            getAuctionResponseDto.setAuctionList(auctionList);
            getAuctionResponseDto.setMessage("Get successfully");

        } catch (SQLException e) {
            getAuctionResponseDto.setMessage(e.getMessage());
        } catch (Exception e) {
            getAuctionResponseDto.setMessage(e.getMessage());
            e.printStackTrace();
        }

        Gson gson = Converters.registerAll(new GsonBuilder()).create();
        this.response = gson.toJson(getAuctionResponseDto);

        super.handle(exchange);

    }
}
