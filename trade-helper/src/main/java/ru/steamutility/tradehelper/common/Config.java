package ru.steamutility.tradehelper.common;

import javafx.util.Pair;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Config {
    private static final File config = new File(AppPlatform.getConfigPath());

    static {
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    public static String getProperty(String property) {
        String value = null;
        try(BufferedReader br = new BufferedReader(new FileReader(config))) {
            while(br.ready()) {
                String s = br.readLine();
                Pair<String, String> res = Util.getPropertyPair(s);
                if(Objects.requireNonNull(res).getKey().equalsIgnoreCase(property)) {
                    value = res.getValue();
                }
            }
        } catch (IOException ignored) {
        }
        return value; // return null if property not found
    }

    public static int getPropertyInt(String property) {
        String s = getProperty(property);
        int res = -1;
        try {
            res = Integer.parseInt(s);
        } catch (Exception ignored) {
        }
        return res;
    }

    public static double getPropertyDouble(String property) {
        String s = getProperty(property);
        double res = 0;
        try {
            s = s.replaceAll(",", ".");
            res = Double.parseDouble(s);
        } catch (Exception ignored) {
        }
        return res;
    }
}
