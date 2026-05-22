package com.auction.server.auction;

import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BaseRequestDTO;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.dao.BidDAO;
import com.auction.server.exception.DatabaseException;

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
    //
    public BidUpdateResponseDTO normalBid(BaseRequestDTO bidRequestDTO) throws DatabaseException {
        BidUpdateResponseDTO bidUpdateResponseDTO=new BidUpdateResponseDTO();
        BidRequestDTO bid= (BidRequestDTO) bidRequestDTO;

        int bidderId=bid.getBidderId();
        int auctionId=bid.getAuctionId();
        double bidAmmount=bid.getBidAmount();
        double highCurrentPrice=bid.getHighCurrentPrice();

        ReentrantReadWriteLock reentrantReadWriteLock=getReadWriteLock(auctionId);
        reentrantReadWriteLock.writeLock().lock();
        try{
            if (bidAmmount<=highCurrentPrice){
                bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
                bidUpdateResponseDTO.setBidderId(bidderId);
            }
            else {
/*                new AuctionDAO().updateCurrentPrice(auctionId,bidAmmount,bidderId);*/
                new BidDAO().placeBid(auctionId,bidderId,bidAmmount);

                bidUpdateResponseDTO=new BidUpdateResponseDTO(auctionId,bidderId,bidAmmount);
                bidUpdateResponseDTO.setBidStatus(BidStatus.SUCCESS);
            }


            return bidUpdateResponseDTO;
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

    }

    public BidUpdateResponseDTO autoBid(BaseRequestDTO autoBidRequestDTO) throws DatabaseException {
        BidUpdateResponseDTO bidUpdateResponseDTO=new BidUpdateResponseDTO();
        AutoBidRequestDTO autoBid=(AutoBidRequestDTO) autoBidRequestDTO;

        int auctionRoomId=autoBid.getAuctionId();
        int bidderId=autoBid.getBidderId();
        double maxBid=autoBid.getMaxBid();
        double increment=autoBid.getIncrement();

        Auction currentAuction=new AuctionDAO().getAuctionInfoById(auctionRoomId);

        ReentrantReadWriteLock reentrantReadWriteLock=getReadWriteLock(auctionRoomId);
        reentrantReadWriteLock.writeLock().lock();

        try {
            if (maxBid<=currentAuction.getCurrentHighestPrice()){
                bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
                bidUpdateResponseDTO.setBidderId(bidderId);
                bidUpdateResponseDTO.setAuctionId(auctionRoomId);
                bidUpdateResponseDTO.setNewHighestPrice(currentAuction.getCurrentHighestPrice());

            }
            else {
                double highestCurrentPrice=currentAuction.getCurrentHighestPrice();

                if (highestCurrentPrice+increment>maxBid){

                    bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
                    bidUpdateResponseDTO.setBidderId(bidderId);
                    bidUpdateResponseDTO.setAuctionId(auctionRoomId);
                    bidUpdateResponseDTO.setNewHighestPrice(currentAuction.getCurrentHighestPrice());

                }

                else {
                    double bidAmount=highestCurrentPrice+increment;

/*                    new AuctionDAO().updateCurrentPrice(auctionRoomId,bidAmount,bidderId);*/
                    new BidDAO().placeBid(auctionRoomId,bidderId,bidAmount);

                    bidUpdateResponseDTO = new BidUpdateResponseDTO(auctionRoomId,bidderId,bidAmount);
                    bidUpdateResponseDTO.setBidStatus(BidStatus.SUCCESS);

                }

            }
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

        return bidUpdateResponseDTO;




    }



}
