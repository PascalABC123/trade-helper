package ru.steamutility.tradehelper.common;

import javafx.util.Pair;

import java.util.Date;

public class Util {
    private Util() {

    }

    public static boolean areDatesEqualByDay(Date d1, Date d2) {
        return d1.getDay() == d2.getDay() &&
                d1.getMonth() == d2.getMonth() &&
                d1.getYear() == d2.getYear();
    }

    /**
     * Replaces all "," by "." and deletes all measurement data from string
     */
    public static double parseDouble(String d) {
        d = d.replaceAll("[^0-9\\,\\.]*", "");
        if (d.charAt(d.length() - 1) == '.')
            d = d.substring(0, d.length() - 1);
        int comma = 0;
        for (int i = 0; i < d.length(); i++) {
            if (d.charAt(i) == ',')
                comma++;
        }
        boolean hasPoint = d.contains(".");
        if (comma == 1 && !hasPoint)
            d = d.replaceAll(",", ".").trim();
        if (comma > 1 || hasPoint)
            d = d.replaceAll(",", "").trim();
        return Double.parseDouble(d);
    }

    /*
     * Config
     */

    /**
     * checks if config line is property
     *
     * @param property raw config line
     * @return is line a property
     */
    public static boolean isProperty(String property) {
        property = property.trim().toLowerCase();
        return property.contains(":") && property.charAt(0) == '#';
    }

    /**
     * Fetches property:value pair from line.
     * If String is not a property returns {"", ""}.
     *
     * @param property raw config line
     * @return property:value pair
     */
    public static Pair<String, String> getPropertyPair(String property) {
        if (!isProperty(property)) return new Pair<>("", "");

        String key = property.split(":")[0];
        String value = property.substring(key.length() + 1).split("//")[0].trim();
        key = key.toLowerCase().trim().substring(1);

        return new Pair<>(key, value);
    }

    /**
     * Replaces value in a property line.
     * If is not a property line returns null.
     *
     * @param property raw property line
     * @param value    value to put
     * @return ready property line
     */
    public static String replacePropertyValue(String property, String value) {
        if (!isProperty(property)) return null;

        String key = property.split(":")[0];
        return key + ": " + value;
    }

    public static String makePropertyLine(String key, String value) {
        return "#" + key + ": " + value;
    }
}
