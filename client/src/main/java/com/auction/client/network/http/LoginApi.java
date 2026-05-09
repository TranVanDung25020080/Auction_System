package com.auction.client.network.http;

import com.auction.common.dto.request.LoginRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;

public class LoginApi {
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) throws IOException {
        String jsonRequest=new Gson().toJson(loginRequestDTO);
        URL url=new URL("http://localhost:8000/login");


        String jsonReponse=BaseApi.getJsonReponse(jsonRequest,url, HttpMethod.POST);

        return new Gson().fromJson(jsonReponse.toString(),UserResponseDTO.class);


    }
}
