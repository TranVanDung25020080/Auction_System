package com.auction.client.service;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.client.network.socket.ClientSocket;
import com.auction.common.dto.response.AuctionResultResponseDTO;
import com.auction.common.dto.response.BaseResponse;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.AuctionStatus;
import com.auction.common.enums.ReponseType;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.util.ISO8601Utils;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Properties;

public class AuctionRoomService {
    public ClientSocket joinRoom(int userId, int auctionRoomId, BiddingPopupController biddingPopupController) throws IOException {
//        Socket socket=new Socket("100.65.119.25",6969);
        /*Socket socket=new Socket("localhost",6969);*/
        Properties prop = new Properties();
        String host = "localhost";
        int port = 6969;

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
            host = prop.getProperty("server.socket.host", "localhost");
            port = Integer.parseInt(prop.getProperty("server.socket.port", "6969"));
        } catch (IOException | NumberFormatException e) {
            // Fallback về localhost nếu không đọc được file cấu hình
        }

        // Kết nối đến Socket Server qua đường hầm Public Serveo
        Socket socket = new Socket(host, port);



        ClientSocket clientSocket=new ClientSocket(socket,userId);

        JoinRoomResponseDTO joinRoomResponseDTO=clientSocket.getJoinRoomResponse(auctionRoomId);

        if (joinRoomResponseDTO.getAuctionStatus()== AuctionStatus.PENDING || joinRoomResponseDTO.getAuctionStatus()==AuctionStatus.OPEN){
            //Luon nghe thong tin tu server tra ve:
            listenAnouncement(biddingPopupController,clientSocket);
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
                Gson gson= Converters.registerAll(new GsonBuilder()).create();

                String jsonReponse;

                try{
                    while ((jsonReponse=bufferedReader.readLine())!=null){
                        String finalJsonResponse=jsonReponse;

                        System.out.println(finalJsonResponse);

                        JsonObject jsonObject= JsonParser.parseString(finalJsonResponse).getAsJsonObject();

                        String responseType=jsonObject.get("responseType").getAsString();
                        ReponseType type=ReponseType.valueOf(responseType);

                        BaseResponse response=null;

                        if (type==ReponseType.BID_UPDATE){
                            response=gson.fromJson(finalJsonResponse, BidUpdateResponseDTO.class);
                        }
                        else if (type==ReponseType.JOIN_ROOM){
                            response=gson.fromJson(finalJsonResponse, JoinRoomResponseDTO.class);
                        }
                        else if (type==ReponseType.AUCTION_RESULT){
                            response=gson.fromJson(finalJsonResponse, AuctionResultResponseDTO.class);

                        }

                        if (response != null) {
                            BaseResponse finalResponse = response;

                            Platform.runLater(() -> {
                                biddingPopupController.setLblStatus(finalResponse.displayMessage());

                                if (finalResponse instanceof BidUpdateResponseDTO) {
                                    BidUpdateResponseDTO bid = (BidUpdateResponseDTO) finalResponse;

                                    LocalDateTime endTime=bid.getEndTime();
                                    biddingPopupController.setEndTime(endTime);

                                    biddingPopupController.startCountdown(endTime);
                                    biddingPopupController.updateHighCurrentPrice(bid.getNewHighestPrice());
                                    biddingPopupController.setLabelCurrentPrice();
                                }

                                else if (finalResponse instanceof AuctionResultResponseDTO){

                                    AuctionResultResponseDTO auctionResultResponseDTO= (AuctionResultResponseDTO) finalResponse;

                                    AuctionStatus auctionStatus=auctionResultResponseDTO.getStatus();

                                    if (auctionStatus==AuctionStatus.FINISHED){
                                        biddingPopupController.endAuction();
                                    }


                                }
                            });
                        }



                    }
                } catch (IOException e) {
                    Alert.showAlert("ERROR",e.getMessage());
                }


            }
        }).start();
    }
}
