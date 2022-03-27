package ru.steamutility.tradehelper.getrequest;

import org.json.JSONObject;
import ru.steamutility.tradehelper.common.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApisGetRequest extends GetRequest {
    private static final int tmrDelay = 3000; // milliseconds

    private static final String baseItemsPath = "https://api.steamapis.com/market/items/";

    private static final List<GetRequestType> types = new ArrayList<>();

    static {
        types.add(GetRequestType.APIS_GET_ITEMS);
    }

    public ApisGetRequest() {
        setTypes(types);
    }

    @Override
    public String makeRequest(GetRequestType type, String... args) {
        String result;
        try {
            result = makeRequest(getRequestPath(type, args));

            final var jsonResult = new JSONObject(result);

            if (jsonResult.has("error")) {
                return null;
            }

            if (jsonResult.has("status") && jsonResult.getInt("status") == 429) {
                throw new ToMuchRequestsException();
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
    public String getRequestPath(GetRequestType type, String... args) {
        switch (type) {
            case APIS_GET_ITEMS -> {
                return apisGetItems(args);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private String apisGetItems(String... args) {
        String apiKey;
        if (args.length == 0)
            apiKey = Config.getProperty("apisApiKey");
        else
            apiKey = args[0];
        return baseItemsPath + "730?api_key=" + apiKey;
    }
}
