package ru.steamutility.tradehelper;

import ru.steamutility.tradehelper.request.SteamMarketRequest;

public class Economy {
    private static String reference = "AK-47%20%7C%20Redline%20%28Field-Tested%29";
    private static double USDRate;

    public enum Currency {
        USD,
        RUB
    }

    public static double getUSDRate() {
        var r = new SteamMarketRequest();
        double usd = Double.parseDouble(r.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_PRICE, reference, "1")
                .getString("lowest_price").replaceAll("\\$", ""));
        double rub = Double.parseDouble(r.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_PRICE, reference, "5")
                .getString("lowest_price").replaceAll(" pуб\\.", "").replaceAll(",", "."));
        USDRate = rub / usd;
        return USDRate;
    }
}
