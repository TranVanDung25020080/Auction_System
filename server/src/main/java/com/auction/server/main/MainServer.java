package com.auction.server.main;

import com.auction.common.enums.UserRole;
import com.auction.server.network.MyHttpServer;
import com.auction.server.routes.RoutesConfig;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        RoutesConfig.addAllRoutes();

        MyHttpServer.start();

        System.out.println("http://localhost:8000/login");

    }
}
