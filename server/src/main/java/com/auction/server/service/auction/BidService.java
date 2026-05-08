package com.auction.server.service.auction;

import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.enums.BidStatus;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.dao.BidDAO;
import com.auction.server.exception.DatabaseException;

public class BidService {
    private final AuctionDAO auctionDAO = new AuctionDAO();
    private final BidDAO bidDAO = new BidDAO();

    public BidUpdateResponseDTO normalBid(BidRequestDTO bidRequestDTO) throws DatabaseException {
        BidUpdateResponseDTO response = new BidUpdateResponseDTO();

        int bidderId = bidRequestDTO.getBidderId();
        int auctionId = bidRequestDTO.getAuctionId();
        double bidAmount = bidRequestDTO.getBidAmount();

        // 1. LẤY DỮ LIỆU THẬT TỪ DATABASE
        Auction auction = auctionDAO.getAuctionInfoById(auctionId);

        if (auction == null) {
            response.setBidStatus(BidStatus.FAILED);
            return response;
        }

        double realCurrentPrice = auction.getCurrentHighestPrice();

        if (bidAmount <= realCurrentPrice) {
            response.setBidStatus(BidStatus.FAILED);
        }
        else {
            boolean isTransactionSuccess = bidDAO.placeBid(auctionId, bidderId, bidAmount);

            if (isTransactionSuccess) {
                auctionDAO.updateCurrentPrice(auctionId, bidAmount, bidderId);

                response = new BidUpdateResponseDTO(auctionId, bidderId, bidAmount);
                response.setBidStatus(BidStatus.SUCCESS);
            } else {
                response.setBidStatus(BidStatus.FAILED);
            }
        }

        return response;
    }
}