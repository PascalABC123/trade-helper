package ru.steamutility.tradehelper.common;

import javafx.util.Pair;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.util.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Config {
    private static final Path config = Path.of(AppPlatform.getConfigPath());
    private static boolean unset = false;

    public static boolean isUnset() {
        return unset;
    }

    static {
        if (!Files.exists(config)) {
            unset = true;
            try {
                Files.createFile(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns null if not found.
     */
    public static String getProperty(String property) {
        String value = null;
        try {
            final List<String> lines = Files.readAllLines(config);
            for(String l : lines) {
                Pair<String, String> res = Util.getPropertyPair(l);
                if (Objects.requireNonNull(res).getKey().equalsIgnoreCase(property)) {
                    value = res.getValue();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Returns -1 if not found.
     */
    public static int getPropertyInt(String property) {
        String s = getProperty(property);
        int res = -1;
        try {
            res = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static double getPropertyDouble(String property) {
        String s = getProperty(property);
        return Util.parseDouble(s);
    }

    public static void setProperty(String line, String value) {
        try {
            List<String> lines = Files.readAllLines(config);
            boolean found = false;
            for(int l = 0; l < lines.size(); l++) {
                Pair<String, String> p = Util.getPropertyPair(lines.get(l));
                if(p.getKey().equalsIgnoreCase(line)) {
                    found = true;
                    lines.add(Util.replacePropertyValue(lines.get(l), value));
                    lines.remove(l);
                    break;
                }
            }
            if(!found) {
                lines.add(Util.makePropertyPair(line, value));
            }
            Files.write(config, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
