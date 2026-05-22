package com.auction.client.network.http;

import com.auction.common.dto.request.DepositBalanceRequestDTO;
import com.auction.common.dto.response.DepositBalanceResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.google.gson.Gson;

import java.io.IOException;

public class UserApi {
    public DepositBalanceResponseDTO depositBalance(DepositBalanceRequestDTO depositBalanceRequestDTO) throws IOException {
        Gson gson=new Gson();

        String route="/depositbalance";

        String jsonRequest=gson.toJson(depositBalanceRequestDTO);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route, HttpMethod.POST);

        return gson.fromJson(jsonResponse, DepositBalanceResponseDTO.class);
    }

}
