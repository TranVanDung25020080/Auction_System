package com.auction.server.service.auction;

import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;

public class BidService {
    public BidUpdateResponseDTO normalBid(BidRequestDTO bidRequestDTO){
        BidUpdateResponseDTO bidUpdateResponseDTO=new BidUpdateResponseDTO();

        int bidderId=bidRequestDTO.getBidderId();
        int auctionId=bidRequestDTO.getAuctionId();
        double bidAmmount=bidRequestDTO.getBidAmount();
        double highCurrentPrice=bidRequestDTO.getHighCurrentPrice();

        if (bidAmmount<=highCurrentPrice){
            bidUpdateResponseDTO.setBidStatus(BidStatus.FAILED);
        }
        else {


            bidUpdateResponseDTO=new BidUpdateResponseDTO(auctionId,bidderId,highCurrentPrice);
            bidUpdateResponseDTO.setBidStatus(BidStatus.SUCCESS);
        }


        return bidUpdateResponseDTO;

    }


}
