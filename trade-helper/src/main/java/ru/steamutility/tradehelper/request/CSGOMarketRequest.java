package ru.steamutility.tradehelper.request;

import org.json.JSONObject;

import java.io.IOException;

public record CSGOMarketRequest(String apiKey) {
    public enum RequestType {
        GET_MONEY;
    }

    private static final String marketApiBaseUrl = "https://market.csgo.com/api/";
    private static final String marketV2ApiBaseUrl = marketApiBaseUrl + "v2/";

    public JSONObject makeRequest(RequestType requestType) throws WrongApiKeyException {
        String path = null;
        switch (requestType) {
            case GET_MONEY -> {
                path = marketV2ApiBaseUrl + "get-money?key=" + apiKey;
            }
        }
        JSONObject res;
        try {
            res = new JSONObject(Requests.makeRequest(path));
        } catch (IOException e) {
            throw new WrongApiKeyException();
        }
        if (!res.getBoolean("success"))
            throw new WrongApiKeyException();
        return res;
    }
}
