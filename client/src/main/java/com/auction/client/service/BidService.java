package com.auction.client.service;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
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

public class BidService {
    public void sendBid(BiddingPopupController biddingPopupController,double bidAmount) throws IOException {
        ClientSocket clientSocket=biddingPopupController.getClientSocket();
        Auction currentAuction=biddingPopupController.getCurrentAuction();

        int userId=biddingPopupController.getUserId();
        int auctionRoomId=currentAuction.getAuctionId();
        double highestCurrentPrice=currentAuction.getCurrentHighestPrice();

        BidRequestDTO bidRequestDTO=new BidRequestDTO(userId,auctionRoomId,bidAmount,highestCurrentPrice);

        clientSocket.sendNormalBid(bidRequestDTO);
        listenBidUpdate(biddingPopupController,clientSocket);

    }

    private void listenBidUpdate(BiddingPopupController biddingPopupController,ClientSocket clientSocket){
        Socket socket=clientSocket.getSocket();
        BufferedWriter bufferedWriter=clientSocket.getBufferedWriter();
        BufferedReader bufferedReader=clientSocket.getBufferedReader();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson=new Gson();

                while (socket.isConnected()){
                    String bidUpdateJson;

                    try {
                        while ((bidUpdateJson=bufferedReader.readLine())!=null){
                            String finalMessage=bidUpdateJson;

                            BidUpdateResponseDTO bidUpdateResponseDTO=gson.fromJson(finalMessage, BidUpdateResponseDTO.class);

                            BidStatus bidStatus=bidUpdateResponseDTO.getBidStatus();

                            if (bidStatus==BidStatus.SUCCESS){
                                //Thay doi highestcurrentPrice:
                                biddingPopupController.updateHighCurrentPrice(bidUpdateResponseDTO.getNewHighestPrice());
                                Platform.runLater(() -> {
                                    biddingPopupController.setLabelCurrentPrice();
                                    biddingPopupController.setLblStatus("User " + bidUpdateResponseDTO.getBidderId() +
                                            " has bidded " + bidUpdateResponseDTO.getNewHighestPrice());
                                });
                            }
                            else {
                                Alert.showAlert("ERORR","FAILED TO BID");

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert.showAlert("ERORR",e.getMessage());//chua xu ly duoc loi ky o day
                    }

                }

            }
        }).start();

    }


}
