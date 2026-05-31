package com.auction.server.service.auction;

import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BaseRequestDTO;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.dao.BidDAO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.socketserver.AuctionRoomHandler;
import com.auction.server.handler.socketserver.ClientHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BidService {

    // Map<int,reeandlock> 1 auctionRoomId sẽ có 1 lock riêng
    private final Map<Integer, ReentrantReadWriteLock> reentrantReadWriteLockMap=new ConcurrentHashMap<>();

    //Method for gettting reentrantlock:
    private ReentrantReadWriteLock getReadWriteLock(int auctionId){
        return  reentrantReadWriteLockMap.computeIfAbsent(auctionId,key->new ReentrantReadWriteLock());
    }

    public BidUpdateResponseDTO normalBid(BaseRequestDTO bidRequestDTO, AuctionRoomHandler auctionRoomHandler) throws DatabaseException {
        BidUpdateResponseDTO bidUpdateResponseDTO=new BidUpdateResponseDTO();
        BidRequestDTO bid= (BidRequestDTO) bidRequestDTO;

        int bidderId=bid.getBidderId();
        int auctionId=bid.getAuctionId();
        double bidAmmount=bid.getBidAmount();

        ReentrantReadWriteLock reentrantReadWriteLock=getReadWriteLock(auctionId);
        reentrantReadWriteLock.writeLock().lock();
        reentrantReadWriteLock.readLock().lock();

        try{
            AuctionDAO auctionDAO=new AuctionDAO();
            Auction currentAuction = auctionDAO.getAuctionInfoById(auctionId);

            double highCurrentPrice=currentAuction.getCurrentHighestPrice();


            LocalDateTime endTime=currentAuction.getEndTime();

            if (getTimeLeft(endTime)<=10){

                endTime=endTime.plusSeconds(20);

                auctionDAO.extendEndTime(auctionId,20);
                /*auctionRoomHandler.startCountDown(clientHandler.getAuction(auctionId));*/
                auctionRoomHandler.startCountDown(auctionDAO.getAuctionInfoById(auctionId));
            }


            if (bidAmmount<=highCurrentPrice){
                bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
                bidUpdateResponseDTO.setBidderId(bidderId);
                bidUpdateResponseDTO.setEndTime(endTime);
            }
            else {
/*                new AuctionDAO().updateCurrentPrice(auctionId,bidAmmount,bidderId);*/
                double maxBidDuringAuction=((BidRequestDTO) bidRequestDTO).getGetMaxBidDuringAuction();
                new BidDAO().placeBid(auctionId,bidderId,bidAmmount,maxBidDuringAuction);

                bidUpdateResponseDTO=new BidUpdateResponseDTO(auctionId,bidderId,bidAmmount);
                bidUpdateResponseDTO.setBidStatus(BidStatus.SUCCESS);
                bidUpdateResponseDTO.setEndTime(endTime);

            }


            return bidUpdateResponseDTO;
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
            reentrantReadWriteLock.readLock().unlock();
        }

    }

    public BidUpdateResponseDTO autoBid(BaseRequestDTO autoBidRequestDTO,AuctionRoomHandler auctionRoomHandler) throws DatabaseException {
        BidUpdateResponseDTO bidUpdateResponseDTO=new BidUpdateResponseDTO();
        AutoBidRequestDTO autoBid=(AutoBidRequestDTO) autoBidRequestDTO;

        int auctionRoomId=autoBid.getAuctionId();
        int bidderId=autoBid.getBidderId();
        double maxBid=autoBid.getMaxBid();
        double increment=autoBid.getIncrement();

        /*Auction currentAuction=new AuctionDAO().getAuctionInfoById(auctionRoomId);*/

        ReentrantReadWriteLock reentrantReadWriteLock=getReadWriteLock(auctionRoomId);
        reentrantReadWriteLock.writeLock().lock();
        reentrantReadWriteLock.readLock().lock();


        try {

            AuctionDAO auctionDAO=new AuctionDAO();
            Auction currentAuction = auctionDAO.getAuctionInfoById(auctionRoomId);
            LocalDateTime endTime=currentAuction.getEndTime();

            if (getTimeLeft(endTime)<=10){

                endTime=endTime.plusSeconds(20);

                auctionDAO.extendEndTime(auctionRoomId,20);
/*                auctionRoomHandler.startCountDown(clientHandler.getAuction(auctionRoomId));*/
                 auctionRoomHandler.startCountDown(auctionDAO.getAuctionInfoById(auctionRoomId));
            }

            if (maxBid<=currentAuction.getCurrentHighestPrice()){
                bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
                bidUpdateResponseDTO.setBidderId(bidderId);
                bidUpdateResponseDTO.setAuctionId(auctionRoomId);
                bidUpdateResponseDTO.setNewHighestPrice(currentAuction.getCurrentHighestPrice());
                bidUpdateResponseDTO.setEndTime(endTime);

            }
            else {
                double highestCurrentPrice=currentAuction.getCurrentHighestPrice();

                if (highestCurrentPrice+increment>maxBid){

                    bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
                    bidUpdateResponseDTO.setBidderId(bidderId);
                    bidUpdateResponseDTO.setAuctionId(auctionRoomId);
                    bidUpdateResponseDTO.setNewHighestPrice(currentAuction.getCurrentHighestPrice());
                    bidUpdateResponseDTO.setEndTime(endTime);

                }

                else {
                    double bidAmount=highestCurrentPrice+increment;

/*                    new AuctionDAO().updateCurrentPrice(auctionRoomId,bidAmount,bidderId);*/
                    double maxBidDuringAuction=((AutoBidRequestDTO) autoBidRequestDTO).getMaxBidDuringAuction();
                    new BidDAO().placeBid(auctionRoomId,bidderId,bidAmount,maxBidDuringAuction);

                    bidUpdateResponseDTO = new BidUpdateResponseDTO(auctionRoomId,bidderId,bidAmount);
                    bidUpdateResponseDTO.setBidStatus(BidStatus.SUCCESS);
                    bidUpdateResponseDTO.setEndTime(endTime);


                }

            }
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
            reentrantReadWriteLock.readLock().unlock();

            
        }

        return bidUpdateResponseDTO;

    }

    private int getTimeLeft(LocalDateTime endTime){
        Duration duration=Duration.between(LocalDateTime.now(),endTime);

        return (int) duration.getSeconds();
    }



}
