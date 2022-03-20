package ru.steamutility.tradehelper.util;

import javafx.util.Pair;

import java.util.Locale;

public class Util {

    /*
     * String
     */

    public static boolean isProperty(String property) {
        property = property.trim().toLowerCase();
        return property.contains(":") && property.charAt(0) == '#';
    }

    public static Pair<String, String> getPropertyPair(String property) {
        if(!isProperty(property)) return null;

        String key = property.split(":")[0];
        String value = property.substring(key.length() + 1).split("//")[0].trim();
        key = key.toLowerCase().trim().substring(1);

        return new Pair<>(key, value);
    }
}
