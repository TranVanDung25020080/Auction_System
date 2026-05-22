package com.auction.server.handler.socketserver;

import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BaseRequestDTO;
import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.exception.DatabaseException;
import com.auction.server.service.auction.AuctionRoomService;
import com.auction.server.service.auction.AuctionService;
import com.auction.server.service.auction.BidService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    public void handleAutoBidding(ClientHandler cli) throws DatabaseException, IOException {
        Gson gson= Converters.registerAll(new GsonBuilder()).create();

        for (ClientHandler clientHandler:this.getSortedAutoBidParticipants()){

            BaseRequestDTO autoBidRequestDTO=clientHandler.getAutoBidRequestDTO();

            if (autoBidRequestDTO!=null){

                BidUpdateResponseDTO bidUpdateResponseDTO =new BidService().autoBid(autoBidRequestDTO,this,cli);

                if (bidUpdateResponseDTO.getBidStatus()== BidStatus.SUCCESS){
                    this.broadcast(gson.toJson(bidUpdateResponseDTO));
                }

            }

        }

    }

    //ham sap xep clienthandler tang dan theo maxbid:

    private List<ClientHandler> getSortedAutoBidParticipants() {
        List<ClientHandler> autoBidClients = new ArrayList<>();

        for (ClientHandler client : participants) {
            if (client.getAutoBidRequestDTO() != null) {
                autoBidClients.add(client);
            }
        }
        autoBidClients.sort((c1, c2) -> {
            double maxBid1 = c1.getAutoBidRequestDTO().getMaxBid();
            double maxBid2 = c2.getAutoBidRequestDTO().getMaxBid();
            return Double.compare(maxBid1, maxBid2);
        });

        return autoBidClients;
    }

}
