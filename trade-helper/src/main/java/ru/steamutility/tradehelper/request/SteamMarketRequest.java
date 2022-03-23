package ru.steamutility.tradehelper.request;

import org.json.JSONObject;

import java.io.IOException;

public class SteamMarketRequest {
    public enum RequestType {
        GET_ITEM_PRICE
    }

    public JSONObject makeRequest(RequestType requestType, String ... args) {
        String path = null;
        switch (requestType) {
            case GET_ITEM_PRICE -> {
                path = String.format("https://steamcommunity.com/market/priceoverview/?appid=730&currency=%s&market_hash_name=%s",
                        args[1], args[0]);
            }
        }
        JSONObject res = null;
        res = new JSONObject(Requests.makeSafeRequest(path));
        return res;
    }
}
