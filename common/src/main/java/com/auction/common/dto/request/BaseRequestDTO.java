package com.auction.common.dto.request;

import com.auction.common.enums.ReponseType;
import com.auction.common.enums.RequestType;

public class BaseRequestDTO {
    protected RequestType requestType;

    public RequestType getRequestType(){
        return this.requestType;
    }
}
