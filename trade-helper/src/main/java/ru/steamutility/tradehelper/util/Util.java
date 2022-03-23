package ru.steamutility.tradehelper.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skinnable;
import javafx.scene.layout.Region;
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

    public static double parseDouble(String d) {
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
