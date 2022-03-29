package ru.steamutility.tradehelper.economy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.steamutility.tradehelper.controller.HomeWindowController;
import ru.steamutility.tradehelper.getrequest.GetRequestType;
import ru.steamutility.tradehelper.getrequest.GetRequests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
            final JSONObject object = (JSONObject) bean;
            final Item item = Items.get(object.getString("market_hash_name"));

            try {
                try {
                    item.setIconURL(new URL(object.getString("image")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                final JSONObject prices = object.getJSONObject("prices");
                item.setSteamPrice(prices.getBigDecimal("safe").doubleValue());
                item.setSteamMedianPrice(prices.getBigDecimal("median").doubleValue());
                item.setSteamAvgPrice(prices.getBigDecimal("avg").doubleValue());
                item.setSteamMinPrice(prices.getBigDecimal("min").doubleValue());
                item.setSteamMaxPrice(prices.getBigDecimal("max").doubleValue());
                item.setStable(!prices.getBoolean("unstable"));

                final JSONObject sold = prices.getJSONObject("sold");
                item.setSteam24HVolume(sold.getBigInteger("last_24h").intValue());
                item.setSteamWeekVolume(sold.getBigInteger("last_7d").intValue());
                item.setSteamMonthVolume(sold.getBigInteger("last_30d").intValue());

            } catch (JSONException ignored) {
                // Exception can be thrown if item is unpopular
                // Then item variables stay equal to 0
            }
        }
    }


    private static void initializeUI() {
        TreeSet<Item> items = getFilteredItems(Filters.getFilter(FilterType.STEAM_MIN_PRICE, 1));

        HomeWindowController.initializeItems(null, null);
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

    private static TreeSet<Item> getSortedByPriceSet() {
        return new TreeSet<>((i1, i2) -> {
            Double d1 = i1.getDepositProfit();
            Double d2 = i2.getDepositProfit();
            return d2.compareTo(d1);
        });
    }

    public static TreeSet<Item> getFilteredItems(List<Predicate<Item>> filters) {
        Stream<Item> itemStream = itemList.stream();
        for(Predicate<Item> filter : filters) {
            itemStream = itemStream.filter(filter);
        }
        TreeSet<Item> result = getSortedByPriceSet();
        result.addAll(itemStream.toList());
        return result;
    }

    public static TreeSet<Item> getFilteredItems(Predicate<Item> filter) {
        return getFilteredItems(new ArrayList<>(){{
            add(filter);
        }});
    }
}
