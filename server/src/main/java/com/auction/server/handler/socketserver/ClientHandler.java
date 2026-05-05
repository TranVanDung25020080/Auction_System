package com.auction.server.handler.socketserver;

import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.server.service.auction.AuctionRoomService;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{

    //FIELDS:
    //Socket fields
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    //other fields:
    private int userId;
    private List<Integer> auctionRoomJoinId;

    //Constructor
    public ClientHandler(Socket socket) throws IOException {
        this.socket=socket;
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    //Override
    @Override
    public void run() {
        Gson gson=new Gson();

        try{
            //Join Room and broadcast:
            String joinRoomRequestJson=bufferedReader.readLine();

            JoinRoomRequestDTO joinRoomRequestDTO=gson.fromJson(joinRoomRequestJson, JoinRoomRequestDTO.class);

            JoinRoomResponseDTO joinRoomResponseDTO=new AuctionRoomService().joinRoom(this,joinRoomRequestDTO);

            /*bufferedWriter.write(gson.toJson(joinRoomResponseDTO));
            bufferedWriter.newLine();
            bufferedWriter.flush();*/

            AuctionRoomHandler auctionRoomHandler=AuctionHandler.getAuctionHanlderRoom(joinRoomRequestDTO.getAuctionId());
            auctionRoomHandler.addClientHandler(this);
            /*auctionRoomHandler.broadcast("User "+joinRoomRequestDTO.getUserId()+" has joined room "+joinRoomRequestDTO.getAuctionId());*/
            auctionRoomHandler.broadcast(gson.toJson(joinRoomResponseDTO));


            //Normal bidding:






        } catch (IOException e) {
            e.printStackTrace(); // chua xu ly loi ky o day
        }


    }
    //Method for other classes to call
    public void send(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    //setter
    public void setUserId(int userId){
        this.userId=userId;
    }
}
