package com.auction.server.handler.socketserver;

import java.util.HashMap;
import java.util.Map;

public class AuctionHandler {
    private static Map<Integer,AuctionRoomHandler> auctionRoomHandlerMap=new HashMap<>();

    public static AuctionRoomHandler getAuctionHanlderRoom(int auctionId){
        AuctionRoomHandler auctionRoomHandler=auctionRoomHandlerMap.get(auctionId);

        if (auctionRoomHandler!=null){
            return auctionRoomHandler;
        }

        return null;

    }

}
