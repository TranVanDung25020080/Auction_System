package com.auction.server.handler.socketserver;

import java.util.HashMap;
import java.util.Map;

public class AuctionHandler {
    private static Map<Integer,AuctionRoomHandler> auctionRoomHandlerMap=new HashMap<>();

    public static AuctionRoomHandler getAuctionRoomHandler(int auctionId){

        AuctionRoomHandler auctionRoomHandler=auctionRoomHandlerMap.get(auctionId);

        if (auctionRoomHandler!=null){
            return auctionRoomHandler;
        }

        else {
            auctionRoomHandler=new AuctionRoomHandler();
            auctionRoomHandlerMap.put(auctionId,auctionRoomHandler);

            return auctionRoomHandler;
        }

    }

}
