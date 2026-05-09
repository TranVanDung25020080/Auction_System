package com.auction.server.handler.socketserver;

import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.BaseResponse;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.service.auction.AuctionRoomService;
import com.auction.server.service.auction.BidService;
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

            System.out.println(gson.toJson(joinRoomRequestJson));

            BaseResponse joinRoomResponseDTO=new AuctionRoomService().joinRoom(this,joinRoomRequestDTO);

            bufferedWriter.write(gson.toJson(joinRoomResponseDTO));
            bufferedWriter.newLine();
            bufferedWriter.flush();

            AuctionRoomHandler auctionRoomHandler=AuctionHandler.getAuctionRoomHandler(joinRoomRequestDTO.getAuctionId());
            auctionRoomHandler.addClientHandler(this);
            auctionRoomHandler.broadcast(gson.toJson(joinRoomResponseDTO));

            //Normal bidding:

            while (socket.isConnected()){
                String bidRequstDTOJson=bufferedReader.readLine();

                System.out.println(bidRequstDTOJson);

                BidRequestDTO bidRequestDTO=gson.fromJson(bidRequstDTOJson, BidRequestDTO.class);

                BaseResponse bidUpdateResponseDTO=new BidService().normalBid(bidRequestDTO);

                auctionRoomHandler.broadcast(gson.toJson(bidUpdateResponseDTO));
            }







        } catch (IOException e) {
            e.printStackTrace(); // chua xu ly loi ky o day
        } catch (DatabaseException e) {
            e.printStackTrace(); //chua xu ly loi ky o day

        }


    }

    public void close() throws IOException {
        if (this.socket!=null){
            socket.close();
        }
        if (this.bufferedWriter!=null){
            bufferedWriter.close();
        }
        if (this.bufferedReader!=null){
            bufferedReader.close();
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
