package ru.steamutility.tradehelper.request;

import org.json.JSONObject;

public class CSGOMarketRequest {
    private String apiKey;

    public CSGOMarketRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public CSGOMarketRequest() {

    }

    public enum RequestType {
        GET_ITEM_LIST,
        GET_BALANCE
    }

    private static final String marketApiBaseUrl = "https://market.csgo.com/api/";
    private static final String marketV2ApiBaseUrl = marketApiBaseUrl + "v2/";

    public JSONObject makeRequest(RequestType requestType, String ... args) throws WrongApiKeyException {
        String path = null;
        switch (requestType) {
            case GET_ITEM_LIST -> {
                if(args.length == 0)
                    path = marketV2ApiBaseUrl + "prices/RUB.json";
                if(args.length == 1)
                    path = marketV2ApiBaseUrl + String.format("prices/%s.json", args[0]);
            }
            case GET_BALANCE -> {
                assert apiKey != null;
                path = marketV2ApiBaseUrl + "get-money?key=" + apiKey;
            }
        }
        JSONObject res;
        res = new JSONObject(Requests.makeSafeRequest(path));
        if (!res.getBoolean("success"))
            throw new WrongApiKeyException();
        return res;
    }
}
