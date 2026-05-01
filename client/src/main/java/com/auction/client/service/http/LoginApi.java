package com.auction.client.service.http;

import com.auction.common.dto.request.LoginRequestDTO;
import com.auction.common.dto.response.UserResponseDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginApi {
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) throws IOException {
        URL url = new URL("http://localhost:8000/login");

        HttpURLConnection connection= (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("accept","application/json");

        String jsonRequest=new Gson().toJson(loginRequestDTO);

        try (OutputStream outputStream=connection.getOutputStream()){
            outputStream.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        StringBuilder jsonReponse=new StringBuilder();
        String line;

        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ( (line=reader.readLine())!=null){
            jsonReponse.append(line);
        }

        reader.close();

        return new Gson().fromJson(jsonReponse.toString(),UserResponseDTO.class);


    }
}
