package com.auction.client.service.http;

import com.auction.common.dto.request.LoginRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginApi {
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) throws IOException {
        URL url = new URL("https://localhost:8080/login");

        HttpURLConnection connection= (HttpURLConnection) url.openConnection();


    }
}
