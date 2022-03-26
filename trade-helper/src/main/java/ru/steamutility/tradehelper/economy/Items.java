package ru.steamutility.tradehelper.economy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.request.CSGOMarketRequest;
import ru.steamutility.tradehelper.request.SteamMarketRequest;
import ru.steamutility.tradehelper.request.WrongApiKeyException;

public class Items {
    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static final SortedList<Item> itemSortedList;

    static {
        itemSortedList = new SortedList<>(itemList);
    }

    public static void initMarket() {
        final var request = new CSGOMarketRequest();
        try {
            final JSONArray items = request.makeRequest(CSGOMarketRequest.RequestType.GET_ITEM_LIST).getJSONArray("items");
            for(Object bean : items) {
                JSONObject obj = (JSONObject) bean;
                if(obj.has("market_hash_name")) {
                    Item item = Items.get(obj.getString("market_hash_name"));
                    item.setMarketVolume(obj.getInt("volume"));
                    item.setMarketPrice(obj.getDouble("price"));
                }
            }
        } catch (WrongApiKeyException e) {
            AppPlatform.requestSettingsMenu("Wrong market api key!");
        }
    }

    public static void initSteam() {
        final var request = new SteamMarketRequest();
        final int size = request.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_RANGED_LISTING, "0", "1").getInt("total_count");
        for(int i = 0; i < size; i += 100) {
            final JSONObject res = request.makeRequest(SteamMarketRequest.RequestType.GET_ITEM_RANGED_LISTING, String.valueOf(i), "100");
            final JSONArray items = res.getJSONArray("results");
            for(Object bean : items) {
                JSONObject obj = (JSONObject) bean;
                Item item = get(obj.getString("hash_name"));
                item.setSteamListings(obj.getInt("sell_listings"));
                item.setSteamPrice(obj.getInt("sell_price") / 100.0);
                System.out.println(obj.getString("name"));
            }
        }

    }

    public static synchronized Item get(String hashName) {
        int l = 0, r = itemList.size() - 1;
        while(l <= r) {
            int mid = (l + r) / 2;
            int res = itemSortedList.get(mid).getHashName().compareTo(hashName);
            if(res < 0) {
                l = mid + 1;
            }
            if(res > 0) {
                r = mid - 1;
            }
            if(res == 0) {
                return itemList.get(mid);
            }
        }
        // item not found in array
        Item item = new Item();
        item.setHashName(hashName);
        itemList.add(item);
        return item;
    }

    public enum ItemBaseType {
        PISTOL,
        PP,
        RIFLE,
        HEAVY,
        KNIFE,
        GLOVES,
        AGENT,
        CRATE,
        GRAFFITI,
        STICKER
    }

    public enum ItemRarity {
        CONSUMER,
        MIL_SPEC,
        INDUSTRIAL,
        RESTRICTED,
        CLASSIFIED,
        COVERT,
        BASE,
        SUPERIOR,
        DISTINGUISHED,
        MASTER,
        EXCEPTIONAL,
        EXTRAORDINARY,
        HIGH,
        REMARKABLE,
        EXOTIC,
        CONTRABAND
    }

    public enum ItemExterior {
        FACTORY_NEW,
        MINIMAL_WEAR,
        FILED_TESTED,
        WELL_WORN,
        BATTLE_SCARRED,
        NOT_PAINTED
    }
}
