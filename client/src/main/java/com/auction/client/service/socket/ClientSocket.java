package com.auction.client.service.socket;

import com.auction.common.dto.request.BidRequestDTO;
import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
    public void listenAnouncement(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String message;
                    while ((message=bufferedReader.readLine())!=null){
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
    public void listenBidUpdate(int userId,int auctionId,double currentPrice) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*Scanner scanner=new Scanner(System.in);
                try{
                    Gson gson=new Gson();
                    while (true){
                        double bidAmount=scanner.nextDouble();
                        BidRequestDTO bidRequestDTO=new BidRequestDTO(userId,auctionId,bidAmount,currentPrice);
                        bufferedWriter.write(gson.toJson(bidRequestDTO));
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        }).start();

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
