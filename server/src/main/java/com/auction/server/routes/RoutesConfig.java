package com.auction.server.routes;

import com.auction.server.handler.httpserver.LoginHandler;
import com.auction.server.network.MyHttpServer;

public class RoutesConfig {
    public static void addAllRoutes(){
        MyHttpServer.addRoute("/login",new LoginHandler());

    }
}
