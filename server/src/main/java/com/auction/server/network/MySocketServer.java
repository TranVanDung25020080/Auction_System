package com.auction.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer {
    private static ServerSocket serverSocket;

    public static void start() throws IOException {
        serverSocket=new ServerSocket(8080);

        while (!serverSocket.isClosed()){
            Socket socket=serverSocket.accept();

            Thread thread=new Thread();// truyen vao 1 clienthandler here

            thread.start();
        }

    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
