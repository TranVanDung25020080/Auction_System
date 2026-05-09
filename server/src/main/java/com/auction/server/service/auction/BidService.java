package com.auction.server.service.auction;

import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.exception.DatabaseException;

public class BidService {
    public BidUpdateResponseDTO normalBid(BidRequestDTO bidRequestDTO) throws DatabaseException {
        BidUpdateResponseDTO bidUpdateResponseDTO=new BidUpdateResponseDTO();

        int bidderId=bidRequestDTO.getBidderId();
        int auctionId=bidRequestDTO.getAuctionId();
        double bidAmmount=bidRequestDTO.getBidAmount();
        double highCurrentPrice=bidRequestDTO.getHighCurrentPrice();

        if (bidAmmount<=highCurrentPrice){
            bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
            bidUpdateResponseDTO.setBidderId(bidderId);
        }
        else {
            new AuctionDAO().updateCurrentPrice(auctionId,bidAmmount,bidderId);
            bidUpdateResponseDTO=new BidUpdateResponseDTO(auctionId,bidderId,highCurrentPrice);
            bidUpdateResponseDTO.setBidStatus(BidStatus.SUCCESS);
            bidUpdateResponseDTO.setNewHighestPrice(bidAmmount);
        }


        return bidUpdateResponseDTO;

    }


}
