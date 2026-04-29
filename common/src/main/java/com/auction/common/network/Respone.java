package com.auction.common.network;

public class Respone {
    private boolean success;
    private String message;
    private String payload;

    public Respone(boolean success, String message, String payload) {
        this.success = success;
        this.message = message;
        this.payload = payload;
    }

    //Getter
    //region
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public String getPayload() {
        return payload;
    }
    //endregion
}
