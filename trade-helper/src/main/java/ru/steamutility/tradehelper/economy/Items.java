package ru.steamutility.tradehelper.economy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.steamutility.tradehelper.common.Util;
import ru.steamutility.tradehelper.getrequest.GetRequestType;
import ru.steamutility.tradehelper.getrequest.GetRequests;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.TreeSet;

public class Items {
    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static final SortedList<Item> itemSortedList;

    static {
        itemSortedList = new SortedList<>(itemList);
    }

    public static void initMarket() {
        final JSONObject rawObject = new JSONObject(GetRequests.makeRequest(GetRequestType.MARKET_GET_ITEMS));
        try {
            final JSONArray items = rawObject.getJSONArray("items");
            for (Object bean : items) {
                JSONObject obj = (JSONObject) bean;
                if (obj.has("market_hash_name")) {
                    Item item = Items.get(obj.getString("market_hash_name"));
                    item.setMarketVolume(obj.getInt("volume"));
                    item.setMarketPrice(obj.getDouble("price"));
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    public static void initSteam() {

    }

    public static void initApis() {
        final JSONObject rawObject = new JSONObject(GetRequests.makeRequest(GetRequestType.APIS_GET_ITEMS));
        for(Object bean : rawObject.getJSONArray("data")) {
            JSONObject object = (JSONObject) bean;
            Item item = Items.get(object.getString("market_hash_name"));

            try {
                try {
                    item.setIconURL(new URL(object.getString("image")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                JSONObject prices = object.getJSONObject("prices");
                item.setSteamPrice(prices.getBigDecimal("safe").doubleValue());
                item.setSteamMedianPrice(prices.getBigDecimal("median").doubleValue());
                item.setSteamAvgPrice(prices.getBigDecimal("avg").doubleValue());
                item.setStable(!prices.getBoolean("unstable"));

                JSONObject sold = prices.getJSONObject("sold");
                item.setSteam24HVolume(sold.getBigInteger("last_24h").intValue());
                item.setSteamWeekVolume(sold.getBigInteger("last_7d").intValue());
                item.setSteamMonthVolume(sold.getBigInteger("last_30d").intValue());

            } catch (JSONException ignored) {
                // Exception can be thrown if item is unpopular
                // Then item variables stay equal to 0
            }
        }
    }

    public static synchronized Item get(String hashName) {
        int l = 0, r = itemList.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int res = itemSortedList.get(mid).getHashName().compareTo(hashName);
            if (res < 0) {
                l = mid + 1;
            }
            if (res > 0) {
                r = mid - 1;
            }
            if (res == 0) {
                return itemList.get(mid);
            }
        }
        // item not found in array
        Item item = new Item();
        item.setHashName(hashName);
        itemList.add(item);
        return item;
    }

    public static ObservableList<Item> getItemList() {
        return itemList;
    }
}
