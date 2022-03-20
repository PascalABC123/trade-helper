package ru.steamutility.tradehelper.request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class CSGOMarketRequest {
    String apiKey;

    public enum RequestType {
        GET_MONEY;
    }

    public CSGOMarketRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public JSONObject makeRequest(RequestType requestType) throws WrongApiKeyException {
        String path = null;
        switch (requestType) {
            case GET_MONEY -> {
                path = "https://market.csgo.com/api/v2/get-money?key=" + apiKey;
            }
        }
        JSONObject res;
        try {
            res = new JSONObject(Requests.makeRequest(path));
        } catch (IOException e) {
            throw new WrongApiKeyException();
        }
        if(!res.getBoolean("success"))
            throw new WrongApiKeyException();
        return res;
    }
}
