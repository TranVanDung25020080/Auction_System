package com.auction.common.network;

import com.auction.common.enums.Action;

public class Request {
    private Action action;
    private String payload;

    public Request(Action action, String payload) {
        this.action = action;
        this.payload = payload;
    }

    //Getter
    //region
    public String getPayload() {
        return payload;
    }
    public Action getAction() {
        return action;
    }
    //endregion
}
