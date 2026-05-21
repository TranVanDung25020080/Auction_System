package com.auction.server.routes;

import com.auction.server.handler.httpserver.*;
import com.auction.server.network.MyHttpServer;

import java.io.IOException;

public class RoutesConfig {
    public static void addAllRoutes() throws IOException {
        MyHttpServer.addRoute(8000,"/login",new LoginHandler());
        MyHttpServer.addRoute("/signup",new SingUpHandler());
        MyHttpServer.addRoute("/getallauction",new GetAuctionHandler());
        MyHttpServer.addRoute("/getitem/sellerid",new GetItemHandler());
        MyHttpServer.addRoute("/additem",new AddItemHandler());
        MyHttpServer.addRoute("/removeitem",new RemoveItemHandler());
        MyHttpServer.addRoute("/createauction",new CreateAuctionHandler());
        MyHttpServer.addRoute("/getauction/sellerid",new GetAuctionBySellerIdHandler());

    }
}
