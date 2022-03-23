package ru.steamutility.tradehelper;

import ru.steamutility.tradehelper.common.USDRateHistory;
import ru.steamutility.tradehelper.request.SteamMarketRequest;
import ru.steamutility.tradehelper.util.Util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class Economy {
    private static final String reference = "AWP%20%7C%20Fade%20%28Factory%20New%29";
    private static double USDRate = USDRateHistory.getUsdRateByDate(new Date());

    public enum Currency {
        USD,
        RUB
    }

    public static double getUSDRate() {
        if(USDRate == 0) {
            var r = new SteamMarketRequest();
            double usd = Util.parseDouble(r.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_PRICE, reference, "1")
                    .getString("lowest_price").replaceAll("\\$", ""));
            double rub = Util.parseDouble(r.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_PRICE, reference, "5")
                    .getString("lowest_price").replaceAll(" pуб\\.", "").replaceAll(",", "."));
            USDRate = rub / usd;
        }
        return USDRate;
    }

    public static String getUSDRateString() {
        return new DecimalFormat("#0.00").format(getUSDRate());
    }

    public static double getUSDDelta() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        return USDRateHistory.getUsdRateByDate(d) - USDRateHistory.getUsdRateByDate(new Date());
    }
}
