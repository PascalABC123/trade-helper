package ru.steamutility.tradehelper;

import ru.steamutility.tradehelper.common.Config;
import ru.steamutility.tradehelper.request.CSGOMarketRequest;
import ru.steamutility.tradehelper.request.WrongApiKeyException;

public class CSGOMarketApiClient {

    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private static CSGOMarketApiClient singleton;

    private CSGOMarketApiClient() {
    }

    public static synchronized CSGOMarketApiClient getSingleton() {
        if(singleton == null) singleton = new CSGOMarketApiClient();
        return singleton;
    }

    public static boolean isConfigKeyValid() {
        return isKeyValid(Config.getProperty("marketApiKey"));
    }

    public static boolean isKeyValid(String key) {
        var r = new CSGOMarketRequest(key);
        try {
            r.makeRequest(CSGOMarketRequest.RequestType.GET_MONEY);
            return true;
        } catch (WrongApiKeyException e) {
            return false;
        }
    }
}
