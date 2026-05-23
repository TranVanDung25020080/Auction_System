package com.auction.server.handler.httpserver;

import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.enums.GetBidInfoType;
import com.auction.server.handler.HttpBaseHandler;
import com.auction.server.service.auction.BidService;
import com.auction.server.service.user.UserService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class GetBidInfoHandler extends HttpBaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson= Converters.registerAll(new GsonBuilder()).create();

        String jsonResponse=super.getRequest(exchange);
        System.out.println(jsonResponse);

        GetBidInfoRequestDTO getBidInfoRequestDTO=gson.fromJson(jsonResponse, GetBidInfoRequestDTO.class);
        GetBidInfoType getBidInfoType=getBidInfoRequestDTO.getBidInfoType();

        GetBidInfoResponseDTO getBidInfoResponseDTO=new GetBidInfoResponseDTO();
        UserService userService=new UserService();

        if (getBidInfoType==GetBidInfoType.BIDDER_ID){
            getBidInfoResponseDTO=userService.getBidInfoByBidderId(getBidInfoRequestDTO);

        }
        else if (getBidInfoType==GetBidInfoType.AUCTION_ID){
            getBidInfoResponseDTO=userService.getBidInfoByAuctionId(getBidInfoRequestDTO);

        }
        this.response=gson.toJson(getBidInfoResponseDTO);
        super.handle(exchange);



    }
}
