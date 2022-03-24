package ru.steamutility.tradehelper.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    private static final int connectTimeout = 1000;
    private static final int readTimeout = 1000;

    /**
     * Makes extra requests if there is an Exception (up to 50 requests)
     */
    public static String makeSafeRequest(String path) {
        int cnt = 50;
        String res = null;
        while(cnt-- > 0) {
            boolean successful = true;
            try {
                res = makeRequest(path);
            } catch (IOException e) {
                successful = false;
            }
            if(successful)
                break;
        }
        return res;

    }

    public static String makeRequest(String path) throws IOException {
        String res;
        final URL url = new URL(path);
        final var connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);

        final var in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        final var content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        res = content.toString();

        // TODO delete this line
        System.out.println(res);
        return res;
    }
}
