package ru.steamutility.tradehelper.request;

import org.json.JSONObject;

public class SteamMarketRequest {

    public enum RequestType {
        GET_ITEM_PRICE,
        GET_ITEM_RANGED_LISTING
    }

    private static final String steamBaseUrl = "https://steamcommunity.com/market/";

    public JSONObject makeRequest(RequestType requestType, String ... args) {
        String path = null;

        switch (requestType) {
            case GET_ITEM_PRICE -> {
                assert args.length == 2;
                path = String.format(steamBaseUrl + "priceoverview/?appid=730&currency=%s&market_hash_name=%s", args[1], args[0]);
            }
            case GET_ITEM_RANGED_LISTING -> {
                assert args.length == 2;
                path = String.format(steamBaseUrl + "search/render/?start=%s&count=%s&sort_column=null&sort_dir=null&appid=730&norender=1", args[0], args[1]);
            }
        }

        return new JSONObject(Requests.makeSafeRequest(path));
    }
}
