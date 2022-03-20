package ru.steamutility.tradehelper.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    private static final int connectTimeout = 1000;
    private static final int readTimeout = 1000;

    public static String makeRequest(String path) throws IOException {
        String res = null;
        URL url = new URL(path);
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        var in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        var content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        res = content.toString();
        System.out.println(res);
        return res;
    }
}
