package ru.steamutility.tradehelper.economy;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class Item implements Comparable {
    private ItemBaseType baseType;
    private ItemRarity rarity;
    private ItemExterior exterior;

    private boolean stable;
    private URL iconURL;
    private String hashName;
    private double steamPrice, marketPrice;
    private double steamAvgPrice;
    private double steamMedianPrice, marketMedianPrice;
    private double steamMaxPrice, steamMinPrice;
    private int marketVolume;
    private int steam24HVolume, steamWeekVolume, steamMonthVolume;
    private int steamAvgVolume;
    private int steamListings;

    public double getDepositProfit() {
        return steamPrice / marketPrice;
    }

    public int getDepositProfitPercent() {
        return (int) (getDepositProfit() * 100) - 100;
    }

    protected Item() {

    }

    @Override
    public int compareTo(@NotNull Object bean) {
        return this.hashName.compareTo(((Item) bean).hashName);
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

    public ItemRarity getRarity() {
        return rarity;
    }

    public void setRarity(ItemRarity rarity) {
        this.rarity = rarity;
    }

    public ItemExterior getExterior() {
        return exterior;
    }

    public void setExterior(ItemExterior exterior) {
        this.exterior = exterior;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }

    public ItemBaseType getBaseType() {
        return baseType;
    }

    public void setBaseType(ItemBaseType baseType) {
        this.baseType = baseType;
    }

    public URL getIconURL() {
        return iconURL;
    }

    public void setIconURL(URL iconURL) {
        this.iconURL = iconURL;
    }

    public int getSteamListings() {
        return steamListings;
    }

    public void setSteamListings(int steamListings) {
        this.steamListings = steamListings;
    }

    public int getMarketListings() {
        return marketListings;
    }

    public void setMarketListings(int marketListings) {
        this.marketListings = marketListings;
    }

    private int marketListings;

    public String getHashName() {
        return hashName;
    }

    public double getSteamAvgPrice() {
        return steamAvgPrice;
    }

    public void setSteamAvgPrice(double steamAvgPrice) {
        this.steamAvgPrice = steamAvgPrice;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public double getSteamPrice() {
        return steamPrice;
    }

    public void setSteamPrice(double steamPrice) {
        this.steamPrice = steamPrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getSteamMedianPrice() {
        return steamMedianPrice;
    }

    public void setSteamMedianPrice(double steamMedianPrice) {
        this.steamMedianPrice = steamMedianPrice;
    }

    public double getMarketMedianPrice() {
        return marketMedianPrice;
    }

    public void setMarketMedianPrice(double marketMedianPrice) {
        this.marketMedianPrice = marketMedianPrice;
    }

    public int getSteam24HVolume() {
        return steam24HVolume;
    }

    public double getSteamMaxPrice() {
        return steamMaxPrice;
    }

    public void setSteamMaxPrice(double steamMaxPrice) {
        this.steamMaxPrice = steamMaxPrice;
    }

    public double getSteamMinPrice() {
        return steamMinPrice;
    }

    public void setSteamMinPrice(double steamMinPrice) {
        this.steamMinPrice = steamMinPrice;
    }

    public void setSteam24HVolume(int steam24HVolume) {
        this.steam24HVolume = steam24HVolume;
    }

    public int getSteamWeekVolume() {
        return steamWeekVolume;
    }

    public void setSteamWeekVolume(int steamWeekVolume) {
        this.steamWeekVolume = steamWeekVolume;
    }

    public int getSteamMonthVolume() {
        return steamMonthVolume;
    }

    public void setSteamMonthVolume(int steamMonthVolume) {
        this.steamMonthVolume = steamMonthVolume;
    }

    public int getMarketVolume() {
        return marketVolume;
    }

    public void setMarketVolume(int marketVolume) {
        this.marketVolume = marketVolume;
    }

    public int getSteamAvgVolume() {
        return steamAvgVolume;
    }

    public void setSteamAvgVolume(int steamAvgVolume) {
        this.steamAvgVolume = steamAvgVolume;
    }
}
