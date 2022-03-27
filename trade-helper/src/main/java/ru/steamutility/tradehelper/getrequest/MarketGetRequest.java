package ru.steamutility.tradehelper.getrequest;

import org.json.JSONObject;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.common.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarketGetRequest extends GetRequest {
    private static final int tmrDelay = 3000; // milliseconds

    private static final String basePath = "https://market.csgo.com/api/v2/";

    private static final List<GetRequestType> types = new ArrayList<>();

    static {
        types.add(GetRequestType.MARKET_GET_BALANCE);
        types.add(GetRequestType.MARKET_GET_ITEMS);
    }

    public MarketGetRequest() {
        setTypes(types);
    }

    @Override
    public String makeRequest(GetRequestType type, String... args) {
        String result;
        try {
            result = makeRequest(getRequestPath(type, args));

            if(!new JSONObject(result).getBoolean("success")) {
                return null;
            }

        } catch (IOException e) {
            return null;

        } catch (ToMuchRequestsException e) {
            try {
                Thread.sleep(tmrDelay);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            result = makeRequest(type);
        }
        return result;
    }

    @Override
    public String getRequestPath(GetRequestType type, String ... args) {
        switch (type) {
            case MARKET_GET_ITEMS -> {
                return marketGetItems(args);
            }
            case MARKET_GET_BALANCE -> {
                return marketGetBalance(args);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private String marketGetItems(String ... args) {
        if(args.length == 0) {
            return basePath + "prices/USD.json";
        }
        else {
            return basePath + String.format("prices/%s.json", args[0]);
        }
    }

    private String marketGetBalance(String... args) {
        String apiKey;
        if (args.length == 0)
            apiKey = Config.getProperty("marketApiKey");
        else
            apiKey = args[0];
        return basePath + "get-money?key=" + apiKey;
    }
}
