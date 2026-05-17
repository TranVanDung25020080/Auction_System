package com.auction.client.network.http;

import com.auction.common.dto.request.RegisterRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.auction.common.enums.HttpMethod;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

public class SignUpApi {
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) throws IOException {
    /*    URL url=new URL("http://localhost:8000/signup");*/
        String route="/signup";
        Gson gson=new Gson();

        String jsonRequest=gson.toJson(registerRequestDTO);

        //Nhan thong tin tu server
        String jsonResponse=BaseApi.getJsonReponse(jsonRequest,route, HttpMethod.POST);

        return gson.fromJson(jsonResponse, UserResponseDTO.class);

    }
}
