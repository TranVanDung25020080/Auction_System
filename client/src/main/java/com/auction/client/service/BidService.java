package com.auction.client.service;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BaseRequestDTO;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.model.Auction.Auction;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BidService {

    public void sendBid(BiddingPopupController biddingPopupController,double bidAmount) throws IOException {
        ClientSocket clientSocket=biddingPopupController.getClientSocket();
        Auction currentAuction=biddingPopupController.getCurrentAuction();

        int userId=biddingPopupController.getUserId();
        int auctionRoomId=currentAuction.getAuctionId();
        double highestCurrentPrice=currentAuction.getCurrentHighestPrice();
        LocalDateTime endTime=currentAuction.getEndTime();

        BaseRequestDTO bidRequestDTO=new BidRequestDTO(userId,auctionRoomId,bidAmount,highestCurrentPrice,endTime);

        clientSocket.sendBiddingInfo(bidRequestDTO);

    }

    public void startAutoBidding(BiddingPopupController biddingPopupController,double maxBid,
                                 double increment) throws IOException {
        ClientSocket clientSocket=biddingPopupController.getClientSocket();
        Auction currentAuction=biddingPopupController.getCurrentAuction();

        int userId=biddingPopupController.getUserId();
        int auctionRoomId=currentAuction.getAuctionId();
        double highestCurrentPrice=currentAuction.getCurrentHighestPrice();
        LocalDateTime endTime=currentAuction.getEndTime();


        BaseRequestDTO autoBidRequestDTO=new AutoBidRequestDTO(auctionRoomId,userId,maxBid,increment,highestCurrentPrice,endTime);

        clientSocket.sendBiddingInfo(autoBidRequestDTO);
    }


}
