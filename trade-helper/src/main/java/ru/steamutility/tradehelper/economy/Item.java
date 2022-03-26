package ru.steamutility.tradehelper.economy;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class Item implements Comparable {
    private Items.ItemBaseType baseType;
    private Items.ItemRarity rarity;
    private Items.ItemExterior exterior;

    private URL iconURL;
    private String hashName;
    private double steamPrice, marketPrice;
    private double steamMedianPrice, marketMedianPrice;
    private int steamVolume, marketVolume;
    private int steamListings;

    protected Item() {

    }

    public Items.ItemBaseType getBaseType() {
        return baseType;
    }

    public void setBaseType(Items.ItemBaseType baseType) {
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

    public int getSteamVolume() {
        return steamVolume;
    }

    public void setSteamVolume(int steamVolume) {
        this.steamVolume = steamVolume;
    }

    public int getMarketVolume() {
        return marketVolume;
    }

    public void setMarketVolume(int marketVolume) {
        this.marketVolume = marketVolume;
    }

    @Override
    public int compareTo(@NotNull Object bean) {
        return this.hashName.compareTo(((Item) bean).hashName);
    }
}
