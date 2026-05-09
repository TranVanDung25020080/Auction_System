package com.auction.client.network.socket;

import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.BidStatus;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    //Socket fields
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //other fields;
    private int userId;

    //Constructor
    public ClientSocket(Socket socket,int userId) throws IOException {
        this.socket=socket;
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        this.userId=userId;
    }
    //method for other classes to call
    public JoinRoomResponseDTO getJoinRoomResponse(int auctionId) throws IOException {
        Gson gson=new Gson();

        JoinRoomRequestDTO joinRoomRequestDTO=new JoinRoomRequestDTO(this.userId,auctionId);

        bufferedWriter.write(gson.toJson(joinRoomRequestDTO));
        bufferedWriter.newLine();
        bufferedWriter.flush();

        String joinRoomReponseDTO=bufferedReader.readLine();

        return gson.fromJson(joinRoomReponseDTO, JoinRoomResponseDTO.class);

    }
    /*public void listenAnouncement(BiddingPopupController biddingPopupController){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String message;
                    while ((message=bufferedReader.readLine())!=null){
                        String finalMessage=message;

                        Platform.runLater(()-> biddingPopupController.setLblStatus(finalMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace(); // chua xy ly loi duoc ky o day
                }
            }
        }).start();

    }
    public void listenBidUpdate(BiddingPopupController biddingPopupController) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                *//*Gson gson=new Gson();
                try{
                    String bidUpdateJson;
                    while ((bidUpdateJson=bufferedReader.readLine())!=null){
                        String finalMessage=bidUpdateJson;

                        BidUpdateResponseDTO bidUpdateResponseDTO=gson.fromJson(finalMessage,BidUpdateResponseDTO.class);

                        BidStatus bidStatus=bidUpdateResponseDTO.getBidStatus();


                    }
                } catch (IOException e) {
                    e.printStackTrace(); // chua xu ly loi ky o day duoc
                }*//*

            }
        }).start();

    }*/
    public void sendNormalBid(BidRequestDTO bidRequestDTO) throws IOException {
        Gson gson=new Gson();
        String bidRequestJson=gson.toJson(bidRequestDTO);

        bufferedWriter.write(bidRequestJson);
        bufferedWriter.newLine();
        bufferedWriter.flush();

    }
    public BufferedReader getBufferedReader(){
        return this.bufferedReader;
    }
    public BufferedWriter getBufferedWriter(){
        return this.bufferedWriter;
    }
    public Socket getSocket(){
        return this.socket;
    }
    public int getUserId(){
        return this.userId;
    }


  /*  //test
    static void main(String[] args) throws IOException {
        Socket socket1=new Socket("localhost",6969);

        int userId=4;

        ClientSocket clientSocket=new ClientSocket(socket1,userId);

        JoinRoomResponseDTO joinRoomResponseDTO=clientSocket.getJoinRoomResponse(5);

        System.out.println(new Gson().toJson(joinRoomResponseDTO));

        clientSocket.listenAnouncement();

        clientSocket.listenBidUpdate(userId,5,100);


    }*/
}
