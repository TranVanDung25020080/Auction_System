package com.auction.server.service.auction;

import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;

import java.sql.SQLException;
import java.util.List;

public class AuctionService {
    public List<Auction> getAllAuctions() throws SQLException {
        List<Auction> auctionList=new AuctionDAO().getAllAuction();

        return  auctionList;
    }

   /* //test
    static void main(String[] args) throws SQLException {
        List<Auction> auctionList=new AuctionService().getAllAuctions();

        System.out.println(auctionList);
    }*/

}
