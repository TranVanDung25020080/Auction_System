package com.auction.server.handler.socketserver;

import com.auction.common.dto.request.AutoBidRequestDTO;
import com.auction.common.dto.request.BaseRequestDTO;
import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.BaseResponse;
import com.auction.common.dto.response.BidUpdateResponseDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.auction.common.enums.RequestType;
import com.auction.common.model.Auction.Auction;
import com.auction.server.dao.AuctionDAO;
import com.auction.server.dao.AutoBidDAO;
import com.auction.server.exception.DatabaseException;
import com.auction.server.service.auction.AuctionRoomService;
import com.auction.server.service.auction.AuctionService;
import com.auction.server.service.auction.BidService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
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
    private AutoBidRequestDTO autoBidRequestDTO;
    private HashMap<Integer,Auction> auctionHashMap=new HashMap<>();


    //Constructor
    public ClientHandler(Socket socket) throws IOException {
        this.socket=socket;
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    //Override
    @Override
    public void run() {
        Gson gson= Converters.registerAll(new GsonBuilder()).create();
        AutoBidDAO autoBidDAO=new AutoBidDAO();


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


            //Start countdown:
            /*Auction auction=new AuctionService().getAuction(joinRoomRequestDTO.getAuctionId());*/
            Auction auction=this.getAuction(joinRoomRequestDTO.getAuctionId());
            auctionRoomHandler.startCountDown(auction);

            //Bidding:
            while (socket.isConnected()){
                String baseRequestDTOJson=bufferedReader.readLine();
                System.out.println(baseRequestDTOJson);

                BaseRequestDTO baseRequestDTO=gson.fromJson(baseRequestDTOJson, BaseRequestDTO.class);
                RequestType requestType=baseRequestDTO.getRequestType();

                if (requestType==RequestType.NORMAL_BIDDING){

                    BidRequestDTO bidRequestDTO=gson.fromJson(baseRequestDTOJson, BidRequestDTO.class);

                    BaseResponse bidUpdateResponseDTO=new BidService().normalBid(bidRequestDTO,auctionRoomHandler,this);

                    auctionRoomHandler.broadcast(gson.toJson(bidUpdateResponseDTO));

                    auctionRoomHandler.handleAutoBidding(this);
                }

                else if(requestType==RequestType.AUTO_BIDDING){

                    AutoBidRequestDTO autoBid=gson.fromJson(baseRequestDTOJson, AutoBidRequestDTO.class);

/*                    int bidderId=autoBid.getBidderId();
                    int auctionId=autoBid.getAuctionId();
                    double maxBid=autoBid.getMaxBid();
                    double increment=autoBid.getIncrement();

                    if (this.autoBidRequestDTO !=null){
                        autoBidDAO.updateAutoBid(bidderId,maxBid,increment);
                    }
                    else {
                        autoBidDAO.insertAutoBid(bidderId,auctionId,maxBid,increment);
                    }*/

                    this.autoBidRequestDTO=autoBid;

                }



            }

        } catch (IOException e) {
            e.printStackTrace();// chua xu ly loi ky o day
        } catch (DatabaseException e) {
            e.printStackTrace();//chua xu ly loi ky o day


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
    public AutoBidRequestDTO getAutoBidRequestDTO(){
        return this.autoBidRequestDTO;
    }

    public Auction getAuction(int auctionId) throws DatabaseException {
        if (this.auctionHashMap.containsKey(auctionId)){
            return this.auctionHashMap.get(auctionId);
        }
        else{
            Auction auction=new AuctionDAO().getAuctionInfoById(auctionId);

            this.auctionHashMap.put(auctionId,auction);

            return  auction;
        }

    }

}
