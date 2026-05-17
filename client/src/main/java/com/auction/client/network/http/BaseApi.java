package com.auction.client.network.http;

import com.auction.common.enums.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BaseApi {
    private static final String BASE_URL="http://localhost:8000";
    public static String getJsonReponse(String jsonRequest, String route, HttpMethod httpMethod) throws IOException {
        URL url=new URL(BASE_URL+route);

        HttpURLConnection connection= (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(httpMethod.toString());
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("accept","application/json");


        try (OutputStream outputStream=connection.getOutputStream()) {

            outputStream.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        StringBuilder jsonReponse=new StringBuilder();
        String line;

        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ( (line=reader.readLine())!=null) {
            jsonReponse.append(line);
        }

        reader.close();

        return jsonReponse.toString();


    }
    //Overload
    public static String getJsonResponse(String route, HttpMethod method) throws IOException {
        URL url=new URL(BASE_URL+route);

        HttpURLConnection connection= (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method.toString());

        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response=new StringBuilder();
        String line;

        while ((line=reader.readLine())!=null){
            response.append(line);
        }
        reader.close();

        return response.toString();


    }
}
