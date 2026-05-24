package com.auction.client.controller.bidderdashboard.buttonhandler;

import com.auction.client.controller.bidderdashboard.BidderDashboardController;

public class ReloadButton {
    public void handle(BidderDashboardController bidderDashboardController){
        bidderDashboardController.renderProducts();

    }
}
