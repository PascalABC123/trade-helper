package ru.steamutility.tradehelper.getrequest;

public enum GetRequestType {
    /*
     * Request type format:
     * [API]_GET_[METHOD]
     */

    // Market requests:
    MARKET_GET_BALANCE,
    MARKET_GET_ITEMS,

    // Steam requests:
    STEAM_GET_ITEM_PRICE,

    // Apis requests:
    APIS_GET_ITEMS,

}
