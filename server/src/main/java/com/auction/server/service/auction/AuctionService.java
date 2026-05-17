package com.auction.server.service.auction;

import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.exception.DatabaseException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionService {
    public List<Auction> getAllAuctions() throws SQLException {
        List<Auction> auctionList=new AuctionDAO().getAllAuction();

        return  auctionList;
    }
    public Map<Integer,Auction> getAllAuction() throws SQLException {
        Map<Integer,Auction> auctionMap=new HashMap<>();

        List<Auction> auctionList=new AuctionDAO().getAllAuction();

        for (Auction auction: auctionList){
            auctionMap.put(auction.getAuctionId(),auction);
        }

        return auctionMap;
    }
    public Auction getAuction(int auctionRoomId) throws DatabaseException {
        Auction auction=new AuctionDAO().getAuctionInfoById(auctionRoomId);

        return auction;
    }


   /* //test
    static void main(String[] args) throws SQLException {
        List<Auction> auctionList=new AuctionService().getAllAuctions();

        System.out.println(auctionList);
    }*/

}
