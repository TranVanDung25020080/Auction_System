package com.auction.server.service.auction;

import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.socketserver.AuctionHandler;
import com.auction.server.handler.socketserver.AuctionRoomHandler;
import com.auction.server.handler.socketserver.ClientHandler;

import java.io.IOException;

public class AuctionRoomService {
    public JoinRoomResponseDTO joinRoom (ClientHandler clientHandler, JoinRoomRequestDTO joinRoomRequestDTO) throws IOException {

        int userId= joinRoomRequestDTO.getUserId();
        int auctionId=joinRoomRequestDTO.getAuctionId();


        clientHandler.setUserId(userId);

        JoinRoomResponseDTO joinRoomResponseDTO=new JoinRoomResponseDTO(userId,auctionId);
        joinRoomResponseDTO.setAuctionStatus(AuctionStatus.PENDING);


        return joinRoomResponseDTO;

    }

    public AuctionResultResponseDTO endAuction(int auctionId) throws DatabaseException {
        AuctionDAO auctionDAO=new AuctionDAO();

        auctionDAO.endAuction(auctionId);

        Auction auction=auctionDAO.getAuctionInfoById(auctionId);

        int winnerId=auction.getWinningBidderId();


        AuctionResultResponseDTO auctionResultResponseDTO= new AuctionResultResponseDTO(winnerId);
        auctionResultResponseDTO.setStatus(AuctionStatus.FINISHED);

        return auctionResultResponseDTO;

    }



}
