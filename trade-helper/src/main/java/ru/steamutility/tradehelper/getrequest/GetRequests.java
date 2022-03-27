package ru.steamutility.tradehelper.getrequest;

import java.util.ArrayList;
import java.util.List;

public class GetRequests {
    private static final List<GetRequest> requests;

    static {
        requests = new ArrayList<>();
        requests.add(new MarketGetRequest());
        requests.add(new SteamGetRequest());
        requests.add(new ApisGetRequest());
    }

    public static String makeRequest(GetRequestType type, String... args) {
        final GetRequest request = getRequest(type);

        assert request != null;
        return request.makeRequest(type, args);
    }

    private static GetRequest getRequest(GetRequestType type) {
        for (GetRequest request : requests) {
            if (request.getTypes().contains(type)) {
                return request;
            }
        }
        return null;
    }
}
