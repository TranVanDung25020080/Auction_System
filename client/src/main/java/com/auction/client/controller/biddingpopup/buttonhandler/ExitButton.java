package com.auction.client.controller.biddingpopup.buttonhandler;

import javafx.animation.Timeline;
import javafx.scene.control.Label;

public class ExitButton {
    public void handle(Timeline timeline, Label lblId){
        if (timeline != null) timeline.stop(); // Dừng đồng hồ để tránh tốn tài nguyên
        lblId.getScene().getWindow().hide();
    }
}
