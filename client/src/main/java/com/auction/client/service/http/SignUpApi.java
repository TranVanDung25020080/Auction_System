package com.auction.client.service.http;

import com.auction.common.dto.request.RegisterRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

public class SignUpApi {
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) throws IOException {
        URL url=new URL("http://localhost:8000/signup");
        Gson gson=new Gson();

        String jsonRequest=gson.toJson(registerRequestDTO);

        //Nhan thong tin tu server
        String jsonReponse=BaseApi.getJsonReponse(jsonRequest,url, HttpMethod.POST);

        return gson.fromJson(jsonReponse, UserResponseDTO.class);

    }
}
