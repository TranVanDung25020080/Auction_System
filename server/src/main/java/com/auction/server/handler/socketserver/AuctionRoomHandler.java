package com.auction.server.handler.socketserver;

import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BaseRequestDTO;
import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.exception.DatabaseException;
import com.auction.server.service.auction.AuctionRoomService;
import com.auction.server.service.auction.AuctionService;
import com.auction.server.service.auction.BidService;
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
    private boolean isSchelude=false;
    private final ScheduledExecutorService executorService= Executors.newSingleThreadScheduledExecutor();

    //Constructor
    public AuctionRoomHandler(){
        this.participants=new ArrayList<>();
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
    public void handleAutoBidding() throws DatabaseException, IOException {
        Gson gson=new Gson();

        for (ClientHandler clientHandler:this.participants){
            BaseRequestDTO autoBidRequestDTO=clientHandler.getAutoBidRequestDTO();

            if (autoBidRequestDTO!=null){

                BidUpdateResponseDTO bidUpdateResponseDTO =new BidService().autoBid(autoBidRequestDTO);

                this.broadcast(gson.toJson(bidUpdateResponseDTO));
            }

        }

    }

}
