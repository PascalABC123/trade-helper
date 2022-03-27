package ru.steamutility.tradehelper.getrequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public abstract class GetRequest {
    private List<GetRequestType> types;

    private static final int connectTimeout = 1000;
    private static final int readTimeout = 1000;

    public abstract String makeRequest(GetRequestType type, String... args);

    public abstract String getRequestPath(GetRequestType type, String... args);

    String makeRequest(String path) throws IOException, ToMuchRequestsException {
        final var url = new URL(path);
        final var connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);

        switch (connection.getResponseCode()) {
            case 200:
                break;
            case 429:
                throw new ToMuchRequestsException();
        }

        final var br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final var res = new StringBuilder();
        while (br.ready()) {
            res.append(br.readLine());
        }
        br.close();
        return res.toString();
    }

    public List<GetRequestType> getTypes() {
        assert types != null;
        return types;
    }

    public void setTypes(List<GetRequestType> types) {
        this.types = types;
    }
}
