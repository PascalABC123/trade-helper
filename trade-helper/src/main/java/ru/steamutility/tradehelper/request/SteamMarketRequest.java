package ru.steamutility.tradehelper.request;

import org.json.JSONObject;

public record SteamMarketRequest() {

    public enum RequestType {
        GET_ITEM_PRICE
    }

    private static final String steamBaseUrl = "https://steamcommunity.com/market/priceoverview/";

    public JSONObject makeRequest(RequestType requestType, String ... args) {
        String path = null;

        switch (requestType) {
            case GET_ITEM_PRICE -> {
                assert args.length == 2;
                path = String.format(steamBaseUrl + "?appid=730&currency=%s&market_hash_name=%s", args[1], args[0]);
            }
        }

        return new JSONObject(Requests.makeSafeRequest(path));
    }
}
