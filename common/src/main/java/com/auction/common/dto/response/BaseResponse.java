package com.auction.common.dto.response;

import com.auction.common.enums.ReponseType;

public class BaseResponse {
    protected ReponseType responseType;
    protected String message;

    //Constructor
    public BaseResponse(){};

    //getter
    public String getMessage(){
        return this.message;
    }
    public ReponseType getReponseType(){
        return this.responseType;
    }
    public String displayMessage(){
        return "";
    };
    //setter
    public void setMessage(String message){
        this.message=message;
    }
}
