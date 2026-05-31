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
import com.auction.server.dao.UserDAO;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable{

    //FIELDS:
    //Socket fields
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    //other fields:
    private int userId;
    private double maxBidDuringAuction;
    private AutoBidRequestDTO autoBidRequestDTO;


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
        UserDAO userDAO=new UserDAO();


        try{
            //Join Room and broadcast:
            String joinRoomRequestJson=bufferedReader.readLine();

            JoinRoomRequestDTO joinRoomRequestDTO=gson.fromJson(joinRoomRequestJson, JoinRoomRequestDTO.class);
            this.userId=joinRoomRequestDTO.getUserId();
            this.maxBidDuringAuction= joinRoomRequestDTO.getMiniWallet();

            System.out.println(gson.toJson(joinRoomRequestJson));

            BaseResponse joinRoomResponseDTO=new AuctionRoomService().joinRoom(this,joinRoomRequestDTO);


            bufferedWriter.write(gson.toJson(joinRoomResponseDTO));
            bufferedWriter.newLine();
            bufferedWriter.flush();

            AuctionRoomHandler auctionRoomHandler=AuctionHandler.getAuctionRoomHandler(joinRoomRequestDTO.getAuctionId());
            auctionRoomHandler.addClientHandler(this);
            auctionRoomHandler.broadcast(gson.toJson(joinRoomResponseDTO));


            //Start countdown:
            Auction auction=new AuctionService().getAuction(joinRoomRequestDTO.getAuctionId());
            /*Auction auction=this.getAuction(joinRoomRequestDTO.getAuctionId());*/
            auctionRoomHandler.startCountDown(auction);

            //Bidding:
            while (socket.isConnected()){
                String baseRequestDTOJson=bufferedReader.readLine();
                System.out.println(baseRequestDTOJson);

                BaseRequestDTO baseRequestDTO=gson.fromJson(baseRequestDTOJson, BaseRequestDTO.class);
                RequestType requestType=baseRequestDTO.getRequestType();

                if (requestType==RequestType.NORMAL_BIDDING){

                    BidRequestDTO bidRequestDTO=gson.fromJson(baseRequestDTOJson, BidRequestDTO.class);

                    BaseResponse bidUpdateResponseDTO=new BidService().normalBid(bidRequestDTO,auctionRoomHandler);

                    auctionRoomHandler.broadcast(gson.toJson(bidUpdateResponseDTO));

                    auctionRoomHandler.handleAutoBidding();
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
    //setter -- getter:
    public void setUserId(int userId){
        this.userId=userId;
    }
    public AutoBidRequestDTO getAutoBidRequestDTO(){
        return this.autoBidRequestDTO;
    }
    public int getUserId(){
        return this.userId;
    }

    public double getMaxBidDuringAuction() {
        return maxBidDuringAuction;
    }
}
//package com.auction.server.handler.socketserver;
//
//import com.auction.common.dto.request.AutoBidRequestDTO;
//import com.auction.common.dto.request.BaseRequestDTO;
//import com.auction.common.dto.request.BidRequestDTO;
//import com.auction.common.dto.request.JoinRoomRequestDTO;
//import com.auction.common.dto.response.BaseResponse;
//import com.auction.common.dto.response.BidUpdateResponseDTO;
//import com.auction.common.dto.response.JoinRoomResponseDTO;
//import com.auction.common.enums.RequestType;
//import com.auction.common.model.Auction.Auction;
//import com.auction.server.dao.AuctionDAO;
//import com.auction.server.dao.AutoBidDAO;
//import com.auction.server.dao.UserDAO;
//import com.auction.server.exception.DatabaseException;
//import com.auction.server.service.auction.AuctionRoomService;
//import com.auction.server.service.auction.AuctionService;
//import com.auction.server.service.auction.BidService;
//import com.fatboyindustrial.gsonjavatime.Converters;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.io.*;
//import java.net.Socket;
//
//public class ClientHandler implements Runnable{
//
//    //FIELDS:
//    private Socket socket;
//    private BufferedReader bufferedReader;
//    private BufferedWriter bufferedWriter;
//
//    //other fields:
//    private int userId;
//    private double maxBidDuringAuction;
//    private AutoBidRequestDTO autoBidRequestDTO;
//
//    // Lấy ra ngoài để lát nữa dọn dẹp lúc client ngắt kết nối
//    private AuctionRoomHandler auctionRoomHandler;
//
//    //Constructor
//    public ClientHandler(Socket socket) throws IOException {
//        this.socket = socket;
//        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//    }
//
//    //Override
//    @Override
//    public void run() {
//        Gson gson = Converters.registerAll(new GsonBuilder()).create();
//        AutoBidDAO autoBidDAO = new AutoBidDAO();
//        UserDAO userDAO = new UserDAO();
//
//        try {
//            //Join Room and broadcast:
//            String joinRoomRequestJson = bufferedReader.readLine();
//
//            // [VÁ LỖI BẢO VỆ]: Tránh trường hợp vừa kết nối đã ngắt luôn
//            if (joinRoomRequestJson == null) {
//                System.out.println("Client ngắt kết nối ngay khi vừa vào phòng.");
//                return;
//            }
//
//            JoinRoomRequestDTO joinRoomRequestDTO = gson.fromJson(joinRoomRequestJson, JoinRoomRequestDTO.class);
//            this.userId = joinRoomRequestDTO.getUserId();
//            this.maxBidDuringAuction = joinRoomRequestDTO.getMiniWallet();
//
//            System.out.println(gson.toJson(joinRoomRequestJson));
//
//            BaseResponse joinRoomResponseDTO = new AuctionRoomService().joinRoom(this, joinRoomRequestDTO);
//
//            // Không cần throws ra ngoài nữa, tự xử lý ghi
//            bufferedWriter.write(gson.toJson(joinRoomResponseDTO));
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//
//            this.auctionRoomHandler = AuctionHandler.getAuctionRoomHandler(joinRoomRequestDTO.getAuctionId());
//            auctionRoomHandler.addClientHandler(this);
//            auctionRoomHandler.broadcast(gson.toJson(joinRoomResponseDTO));
//
//            //Start countdown:
//            Auction auction = new AuctionService().getAuction(joinRoomRequestDTO.getAuctionId());
//            auctionRoomHandler.startCountDown(auction);
//
//            //Bidding:
//            while (socket.isConnected() && !socket.isClosed()) {
//                String baseRequestDTOJson = bufferedReader.readLine();
//
//                // [VÁ LỖI NULL POINTER]: Bắt buộc phải có dòng này!
//                // Nếu client ngắt mạng, readLine() sẽ trả về null. Ta phải break vòng lặp ngay.
//                if (baseRequestDTOJson == null) {
//                    System.out.println("⚠️ User " + this.userId + " đã ngắt kết nối (Rớt mạng/Tắt app).");
//                    break;
//                }
//
//                System.out.println(baseRequestDTOJson);
//
//                BaseRequestDTO baseRequestDTO = gson.fromJson(baseRequestDTOJson, BaseRequestDTO.class);
//
//                // Kiểm tra thêm một lớp an toàn
//                if (baseRequestDTO != null) {
//                    RequestType requestType = baseRequestDTO.getRequestType();
//
//                    if (requestType == RequestType.NORMAL_BIDDING) {
//                        BidRequestDTO bidRequestDTO = gson.fromJson(baseRequestDTOJson, BidRequestDTO.class);
//                        BaseResponse bidUpdateResponseDTO = new BidService().normalBid(bidRequestDTO, auctionRoomHandler);
//                        auctionRoomHandler.broadcast(gson.toJson(bidUpdateResponseDTO));
//                        auctionRoomHandler.handleAutoBidding();
//                    }
//                    else if (requestType == RequestType.AUTO_BIDDING) {
//                        AutoBidRequestDTO autoBid = gson.fromJson(baseRequestDTOJson, AutoBidRequestDTO.class);
//                        this.autoBidRequestDTO = autoBid;
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            System.out.println("⚠️ Lỗi mạng với User " + this.userId + ": " + e.getMessage());
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        } finally {
//            // [VÁ LỖI ZOMBIE CLIENT]: Khi vòng lặp bị phá vỡ (do lỗi hoặc client out),
//            // PHẢI đóng kết nối để dọn dẹp rác bộ nhớ cho Server.
//            try {
//                System.out.println("🧹 Đang dọn dẹp tài nguyên của User " + this.userId);
//                this.close();
//                // Tùy chọn: Nếu class AuctionRoomHandler của bạn có hàm xóa client, hãy gọi nó ở đây
//                // Ví dụ: if (auctionRoomHandler != null) auctionRoomHandler.removeClientHandler(this);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    public void close() throws IOException {
//        if (this.socket != null && !this.socket.isClosed()) {
//            socket.close();
//        }
//        if (this.bufferedWriter != null) {
//            bufferedWriter.close();
//        }
//        if (this.bufferedReader != null) {
//            bufferedReader.close();
//        }
//    }
//
//    // [VÁ LỖI BROKEN PIPE]: Đã bỏ "throws IOException"
//    // Tự bắt lỗi bên trong để không làm sập tiến trình broadcast của Server
//    public void send(String message) {
//        try {
//            bufferedWriter.write(message);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//        } catch (IOException e) {
//            System.out.println("⚠️ Không thể gửi tin nhắn cho User " + this.userId + " (Ống dẫn đã vỡ). Đang ngắt kết nối...");
//            try {
//                this.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    //setter -- getter:
//    public void setUserId(int userId){
//        this.userId = userId;
//    }
//    public AutoBidRequestDTO getAutoBidRequestDTO(){
//        return this.autoBidRequestDTO;
//    }
//    public int getUserId(){
//        return this.userId;
//    }
//    public double getMaxBidDuringAuction() {
//        return maxBidDuringAuction;
//    }
//}