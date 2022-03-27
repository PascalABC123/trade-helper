package ru.steamutility.tradehelper.getrequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SteamGetRequest extends GetRequest {
    private static final int toMuchRequestsDelay = 300000; // milliseconds
    private static final int attemptsNumber = 10;

    private static final String basePath = "https://steamcommunity.com/market/";

    private static final List<GetRequestType> types = new ArrayList<>();

    static {
        types.add(GetRequestType.STEAM_GET_ITEM_PRICE);
    }

    public SteamGetRequest() {
        setTypes(types);
    }

    @Override
    public String makeRequest(GetRequestType type, String... args) {
        boolean succeeded = true;
        int cnt = attemptsNumber;
        String path = getRequestPath(type, args);

        String result = null;
        while (cnt-- > 0) {
            try {
                result = makeRequest(path);
            } catch (IOException e) {
                succeeded = false;
            } catch (ToMuchRequestsException e) {
                succeeded = false;
                try {
                    Thread.sleep(toMuchRequestsDelay);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            if (succeeded) break;
        }
        return result;
    }

    @Override
    public String getRequestPath(GetRequestType type, String... args) {
        switch (type) {
            case STEAM_GET_ITEM_PRICE -> {
                return steamGetItemPrice(args);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }

    private String steamGetItemPrice(String... args) {
        return switch (args.length) {
            case 1 -> basePath + String.format("priceoverview/?appid=730&currency=%s&market_hash_name=%s", "1", args[0]);
            case 2 -> basePath + String.format("priceoverview/?appid=730&currency=%s&market_hash_name=%s", args[1], args[0]);
            default -> throw new IllegalArgumentException();
        };
    }
}
