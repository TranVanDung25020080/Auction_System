package com.auction.server.handler.socketserver;

import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.exception.DatabaseException;
import com.auction.server.service.auction.AuctionRoomService;
import com.auction.server.service.auction.AuctionService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AuctionRoomHandler {
    private int auctionId;
    private List<ClientHandler> participants;
    private List<Integer> successBidderId;
    private boolean isSchelude=false;
    private final ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();

    //Constructor
    public AuctionRoomHandler(){
        this.participants=new ArrayList<>();
        this.successBidderId=new ArrayList<>();
    }
    //getter
    public int getAuctionId(){
        return this.auctionId;
    }
    //setter
    public void addClientHandler(ClientHandler clientHandler){
        this.participants.add(clientHandler);
    }
    //method for other classes to call
    public void broadcast(String message) throws IOException {
        for (ClientHandler clientHandler:participants){
            clientHandler.send(message);
        }
    }
    public void startCountDown(Auction auction){
        if (isSchelude){
            return;
        }
        isSchelude=true;

        int delay=auction.getDurationLeft();

        executorService.schedule(()->{
            try{
                /*Auction finalAuction=new AuctionService().getAuction(auction.getAuctionId());

                int winnerId=finalAuction.getWinningBidderId();

                AuctionResultResponseDTO auctionResultResponseDTO=new AuctionResultResponseDTO(winnerId);*/
                AuctionResultResponseDTO auctionResultResponseDTO=new AuctionRoomService().endAuction(auction.getAuctionId());

                this.broadcast(new Gson().toJson(auctionResultResponseDTO));
            }
            catch (DatabaseException e) {
                e.printStackTrace(); //chua xy ly loi ky o day
            } catch (IOException e) {
                e.printStackTrace(); //chua xy ly loi ky o day
            }


        },delay, TimeUnit.SECONDS);


    }

}
