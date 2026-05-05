package com.auction.client.service.socket;

import com.auction.common.dto.request.JoinRoomRequestDTO;
import com.auction.common.dto.response.JoinRoomResponseDTO;
import com.google.gson.Gson;

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
    public void listenAnouncement(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*try{
                    String message;
                    while ((message=bufferedReader.readLine())!=null){
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            }
        }).start();

    }
    public void listenBidUpdate(){

    }


    /*//test
    static void main(String[] args) throws IOException {
        Socket socket1=new Socket("localhost",6969);

        int userId=4;

        ClientSocket clientSocket=new ClientSocket(socket1,userId);

        JoinRoomResponseDTO joinRoomResponseDTO=clientSocket.getJoinRoomResponse(3);

        System.out.println(new Gson().toJson(joinRoomResponseDTO));

        clientSocket.listenAnouncement();

    }*/
}
