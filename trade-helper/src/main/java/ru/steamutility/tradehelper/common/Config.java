package ru.steamutility.tradehelper.common;

import javafx.util.Pair;
import ru.steamutility.tradehelper.AppPlatform;

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
            for (String l : lines) {
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
        } catch (Exception ignored) {
        }
        return res;
    }

    public static double getPropertyDouble(String property) {
        String s = getProperty(property);
        return Util.parseDouble(s);
    }

    public static void setProperty(String property, String value) {
        final String res = Util.makePropertyLine(property, value);
        try {
            final List<String> lines = Files.readAllLines(config);
            for (int i = 0; i < lines.size(); i++) {
                Pair<String, String> p = Util.getPropertyPair(lines.get(i));
                if (p.getKey().equalsIgnoreCase(property)) {
                    lines.remove(i);
                    break;
                }
            }
            lines.add(res);
            Files.write(config, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
