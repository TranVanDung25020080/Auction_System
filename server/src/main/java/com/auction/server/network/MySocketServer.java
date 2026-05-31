package com.auction.server.network;

import com.auction.server.handler.socketserver.ClientHandler;
import com.auction.server.util.ConfigLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer {
    private static ServerSocket serverSocket;

    public static void start() throws IOException {
        serverSocket=new ServerSocket(6969);

        while (!serverSocket.isClosed()){
            Socket socket=serverSocket.accept();

            Thread thread=new Thread(new ClientHandler(socket));// truyen vao 1 clienthandler here

            thread.start();
        }

    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
