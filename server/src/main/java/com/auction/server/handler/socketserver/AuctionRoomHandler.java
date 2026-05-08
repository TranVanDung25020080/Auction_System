package com.auction.server.handler.socketserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuctionRoomHandler {

    private int auctionId;
    private List<ClientHandler> participants;
    private List<Integer> successBidderId;

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

}
