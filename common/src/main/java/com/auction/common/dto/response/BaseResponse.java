package com.auction.common.dto.response;

import com.auction.common.enums.ReponseType;

public abstract class BaseResponse {
    protected ReponseType responseType;

    public ReponseType getReponseType(){
        return this.responseType;
    }
    public abstract String displayMessage();
}
