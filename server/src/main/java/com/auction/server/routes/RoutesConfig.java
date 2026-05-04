package com.auction.server.routes;

import com.auction.server.handler.httpserver.GetAuctionHandler;
import com.auction.server.handler.httpserver.LoginHandler;
import com.auction.server.handler.httpserver.SingUpHandler;
import com.auction.server.network.MyHttpServer;

import java.io.IOException;

public class RoutesConfig {
    public static void addAllRoutes() throws IOException {
        MyHttpServer.addRoute(8000,"/login",new LoginHandler());
        MyHttpServer.addRoute("/signup",new SingUpHandler());
        MyHttpServer.addRoute("/getallauction",new GetAuctionHandler());

    }
}
