package ru.steamutility.tradehelper;

import org.json.JSONObject;
import ru.steamutility.tradehelper.common.USDRateHistory;
import ru.steamutility.tradehelper.request.SteamMarketRequest;
import ru.steamutility.tradehelper.util.Util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class Economy {
    private static final String referenceItem = "AWP%20%7C%20Fade%20%28Factory%20New%29";
    private static double USDRate = USDRateHistory.getUsdRateByDate(new Date());

    public enum Currency {
        USD,
        RUB
    }

    public static double getUSDRate() {
        if(USDRate == 0) {
            var r = new SteamMarketRequest();
            JSONObject resUSD = r.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_PRICE, referenceItem, "1");
            JSONObject resRub = r.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_PRICE, referenceItem, "5");
            double usd = Util.parseDouble(resUSD.getString("lowest_price"));
            double rub = Util.parseDouble(resRub.getString("lowest_price"));
            USDRate = rub / usd;
        }
        return USDRate;
    }

    public static String getUSDRateString() {
        return new DecimalFormat("#0.00").format(getUSDRate());
    }

    public static double getUSDDelta() {
        Calendar yesterday = Calendar.getInstance();

        yesterday.add(Calendar.DATE, -1);
        Date d = yesterday.getTime();

        double init = USDRateHistory.getUsdRateByDate(d);
        double finite = USDRateHistory.getUsdRateByDate(new Date());

        return init - finite;
    }
}
