package com.auction.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class HttpBaseHandler implements HttpHandler {
    protected String response;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Content-Type","application/json;charset=UTF-8");

        byte[] bytes=response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(200,bytes.length);

        OutputStream outputStream=exchange.getResponseBody();

        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
    public String getRequest(HttpExchange exchange) throws IOException {
        InputStream inputStream=exchange.getRequestBody();

        StringBuilder response=new StringBuilder();
        String line;

        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

        while ((line=reader.readLine())!=null){
            response.append(line);
        }

        return response.toString();
    }
}
