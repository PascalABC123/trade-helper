package ru.steamutility.tradehelper.economy;

import java.util.function.Predicate;

public class Filters {
    public static Predicate<Item> getFilter(FilterType filterType, Object arg) {
        switch (filterType) {
            case STEAM_MAX_PRICE -> {
                return item -> item.getSteamPrice() <= (Double) arg;
            }
            case STEAM_MIN_PRICE -> {
                return item -> item.getSteamPrice() >= (Double) arg;
            }
            case STEAM_MAX_VOLUME -> {
                return item -> item.getSteam24HVolume() <= (Double) arg;
            }
            case STEAM_MIN_VOLUME -> {
                return item -> item.getSteam24HVolume() >= (Double) arg;
            }
            case MARKET_MAX_PRICE -> {
                return item -> item.getMarketPrice() <= (Double) arg;
            }
            case MARKET_MIN_PRICE -> {
                return item -> item.getMarketPrice() >= (Double) arg;
            }
            case MARKET_MAX_VOLUME -> {
                return item -> item.getMarketVolume() <= (Double) arg;
            }
            case MARKET_MIN_VOLUME -> {
                return item -> item.getMarketVolume() >= (Double) arg;
            }
            case STABLE -> {
                return Item::isStable;
            }
            case UNSTABLE -> {
                return item -> !item.isStable();
            }
            default -> {
                return null;
            }
        }
    }
}
