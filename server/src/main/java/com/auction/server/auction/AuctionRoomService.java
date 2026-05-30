package com.auction.server.auction;

import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.dto.response.CreateAuctionResponseDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.common.model.User.User;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.dao.ItemDAO;
import com.auction.server.dao.UserDAO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.handler.socketserver.AuctionHandler;
import com.auction.server.handler.socketserver.AuctionRoomHandler;
import com.auction.server.handler.socketserver.ClientHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AuctionRoomService {
/*    private static final ReentrantReadWriteLock reentrantReadWriteLock=new ReentrantReadWriteLock();
    private static final Lock writeLock= reentrantReadWriteLock.writeLock();
    private static final Lock readLock= reentrantReadWriteLock.readLock();*/
    // Map<int,reeandlock> 1 auctionRoomId sẽ có 1 lock riêng
/*    private final Map<Integer, ReentrantReadWriteLock> reentrantReadWriteLockMap=new ConcurrentHashMap<>();

    //Method for gettting reentrantlock:
    private ReentrantReadWriteLock getReadWriteLock(int auctionId){
        return  reentrantReadWriteLockMap.computeIfAbsent(auctionId,key->new ReentrantReadWriteLock());
    }*/


    public JoinRoomResponseDTO joinRoom (ClientHandler clientHandler, JoinRoomRequestDTO joinRoomRequestDTO) throws IOException, DatabaseException {
        UserDAO userDAO=new UserDAO();

        int userId= joinRoomRequestDTO.getUserId();
        int auctionId=joinRoomRequestDTO.getAuctionId();
        double maxBidDuringAuction=joinRoomRequestDTO.getMiniWallet();

        double currentBalance=userDAO.showBalance(userId);

        if (maxBidDuringAuction<=currentBalance){
            userDAO.updateBalance(userId,currentBalance-maxBidDuringAuction);

        }

        clientHandler.setUserId(userId);

        JoinRoomResponseDTO joinRoomResponseDTO=new JoinRoomResponseDTO(userId,auctionId);
        joinRoomResponseDTO.setAuctionStatus(AuctionStatus.PENDING);
        joinRoomResponseDTO.setCurrentBalance(currentBalance-maxBidDuringAuction);


        return joinRoomResponseDTO;

    }

    public AuctionResultResponseDTO endAuction(int auctionId,AuctionRoomHandler auctionRoomHandler) throws DatabaseException {
       /* readLock.lock();
        writeLock.lock();*/
/*
        ReentrantReadWriteLock reentrantReadWriteLock=getReadWriteLock(auctionId);
        reentrantReadWriteLock.writeLock().lock();
*/

        try{
            AuctionDAO auctionDAO=new AuctionDAO();

            auctionDAO.endAuction(auctionId);

            Auction auction=auctionDAO.getAuctionInfoById(auctionId);

            int winnerId=auction.getWinningBidderId();

            //Hoan tien
            for (ClientHandler clientHandler: auctionRoomHandler.getParticipants()){
                UserDAO userDAO=new UserDAO();

                int userId= clientHandler.getUserId();
                double currentBalance=userDAO.showBalance(userId);

                userDAO.updateBalance(userId,currentBalance+ clientHandler.getMaxBidDuringAuction());

            }

            AuctionResultResponseDTO auctionResultResponseDTO= new AuctionResultResponseDTO(winnerId);
            auctionResultResponseDTO.setStatus(AuctionStatus.FINISHED);

            return auctionResultResponseDTO;
        }
        finally {
            /*readLock.unlock();
            writeLock.unlock();*/
       /*     reentrantReadWriteLock.writeLock().unlock();*/
        }

    }
    public CreateAuctionResponseDTO createAuction(Auction auction) throws DatabaseException, SQLException {
        CreateAuctionResponseDTO createAuctionResponseDTO=new CreateAuctionResponseDTO();

        int sellerId=auction.getSellerId();
        int itemId=auction.getItemId();
        String itemName=auction.getItemName();
        double startPrice=auction.getStartPrice();
        LocalDateTime startTime=auction.getStartTime();
        LocalDateTime endTime=auction.getEndTime();

        new AuctionDAO().createAuction(itemId,itemName,startPrice,sellerId,startTime,endTime);
        new ItemDAO().updateItemStatus(itemId, ItemStatus.AUCTION);

        createAuctionResponseDTO.setAuction(auction);

        return createAuctionResponseDTO;

    }




}
