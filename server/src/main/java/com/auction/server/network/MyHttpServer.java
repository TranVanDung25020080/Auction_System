package com.auction.server.network;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {
    private static HttpServer server;
    public static void addRoute(int port,String route,HttpHandler httpHandler) throws IOException {
        server=HttpServer.create(new InetSocketAddress(port),0);
        server.setExecutor(null);
        server.createContext(route,httpHandler);
    }
    public static void addRoute(String route, HttpHandler httpHandler) {
        server.createContext(route,httpHandler);
    }
    public static void start(){
        server.start();
    }
}
