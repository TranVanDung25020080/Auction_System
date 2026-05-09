package com.auction.client.service;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.AuctionStatus;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class AuctionRoomService {
    public ClientSocket joinRoom(int userId, int auctionRoomId, BiddingPopupController biddingPopupController) throws IOException {
        Socket socket=new Socket("localhost",6969);

        ClientSocket clientSocket=new ClientSocket(socket,userId);

        JoinRoomResponseDTO joinRoomResponseDTO=clientSocket.getJoinRoomResponse(auctionRoomId);

        if (joinRoomResponseDTO.getAuctionStatus()== AuctionStatus.PENDING || joinRoomResponseDTO.getAuctionStatus()==AuctionStatus.OPEN){
/*            //Luon nghe thong tin tu server tra ve:
            listenAnouncement(biddingPopupController,clientSocket);*/

            try{
                BufferedReader bufferedReader=clientSocket.getBufferedReader();

                String message=bufferedReader.readLine();
                if (message!=null){
                    biddingPopupController.setLblStatus(message);
                }


            } catch (IOException e) {
                e.printStackTrace(); // chua xy ly loi duoc ky o day
            }
        }
        else {
            Alert.showAlert("ERORR","This auction room has closed!");
        }

        return clientSocket;

    }

    private void listenAnouncement(BiddingPopupController biddingPopupController,ClientSocket clientSocket){
        BufferedReader bufferedReader=clientSocket.getBufferedReader();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String message;
                    while ((message=bufferedReader.readLine())!=null){
                        String finalMessage=message;

                        Platform.runLater(()-> biddingPopupController.setLabelAnnoucement(finalMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace(); // chua xy ly loi duoc ky o day
                }
            }
        }).start();
    }
}
