package com.auction.client.network.http;

import com.auction.common.dto.request.DepositBalanceRequestDTO;
import com.auction.common.dto.request.GetBidInfoRequestDTO;
import com.auction.common.dto.response.DepositBalanceResponseDTO;
import com.auction.common.dto.response.GetAuctionResponseDTO;
import com.auction.common.dto.response.GetBidInfoResponseDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.GetBidInfoType;
import com.auction.common.enums.HttpMethod;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class UserApi {
    public DepositBalanceResponseDTO depositBalance(DepositBalanceRequestDTO depositBalanceRequestDTO) throws IOException {
        Gson gson=new Gson();

        String route="/depositbalance";

        String jsonRequest=gson.toJson(depositBalanceRequestDTO);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route, HttpMethod.POST);

        return gson.fromJson(jsonResponse, DepositBalanceResponseDTO.class);
    }

    public GetBidInfoResponseDTO getBidInfoByBidderId(GetBidInfoRequestDTO getBidInfoRequestDTO) throws IOException {
        Gson gson= Converters.registerAll(new GsonBuilder()).create();
        String route="/getbidinfo/bidderid";

        String jsonRequest=gson.toJson(getBidInfoRequestDTO);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route,HttpMethod.POST);

        return gson.fromJson(jsonResponse, GetBidInfoResponseDTO.class);

    }

    public GetBidInfoResponseDTO getBidInfoByAuctionId(GetBidInfoRequestDTO getBidInfoRequestDTO) throws IOException {
        Gson gson= Converters.registerAll(new GsonBuilder()).create();
        String route="/getbidinfo/auctionid";

        String jsonRequest=gson.toJson(getBidInfoRequestDTO);

        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route,HttpMethod.POST);

        return gson.fromJson(jsonResponse, GetBidInfoResponseDTO.class);


    }
    public UserResponseDTO getAllUser() throws IOException {
        Gson gson=new Gson();

        String route="/getallusers";

        String jsonResponse=BaseApi.getJsonResponse(route,HttpMethod.GET);

        return gson.fromJson(jsonResponse,UserResponseDTO.class);

    }
   /* //test
    static void main(String[] args) throws IOException {
       UserResponseDTO userResponseDTO=new UserApi().getAllUser();

        System.out.println(userResponseDTO.getUserList());
    }
*/
}
