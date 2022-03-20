package ru.steamutility.tradehelper.util;

import javafx.util.Pair;

public class Util {

    /*
     * Config
     */

    /*
    * @param property raw property line
     */
    public static boolean isProperty(String property) {
        property = property.trim().toLowerCase();
        return property.contains(":") && property.charAt(0) == '#';
    }

    /*
     * @param property raw property line
     */
    public static Pair<String, String> getPropertyPair(String property) {
        if(!isProperty(property)) return new Pair<>("", "");

        String key = property.split(":")[0];
        String value = property.substring(key.length() + 1).split("//")[0].trim();
        key = key.toLowerCase().trim().substring(1);

        return new Pair<>(key, value);
    }

    /*
     * @param property raw property line
     */
    public static String replacePropertyValue(String property, String value) {
        if(!isProperty(property)) return null;

        String key = property.split(":")[0];
        return key + ": " + value;
    }

    public static String makePropertyPair(String key, String value) {
        return "#" + key + ": " + value;
    }
}
