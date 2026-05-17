package com.auction.client.network.socket;

import com.auction.client.controller.biddingpopup.BiddingPopupController;
import com.auction.common.dto.request.BaseRequestDTO;
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
    public void sendBiddingInfo(BaseRequestDTO baseRequestDTO) throws IOException {
        Gson gson=new Gson();
        String bidRequestJson=gson.toJson(baseRequestDTO);

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

}
