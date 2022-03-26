package ru.steamutility.tradehelper.common;

import javafx.util.Pair;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.util.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
        } catch (Exception ignored) {
        }
        return res;
    }

    public static double getPropertyDouble(String property) {
        String s = getProperty(property);
        return Util.parseDouble(s);
    }

    public static void setProperty(String property, String value) {
        String res = Util.makePropertyLine(property, value) + "\n";
        try(final var raf = new RandomAccessFile(config.toFile(), "rw")) {
            boolean found = false;
            long pos = 0;
            while((pos = raf.getFilePointer()) < raf.length()) {
                String line = raf.readLine();
                Pair<String, String> p = Util.getPropertyPair(line);
                if(p.getKey().equalsIgnoreCase(property)) {
                    found = true;
                    raf.seek(pos);
                    raf.write(res.getBytes());
                }
            }
            if(!found) {
                Files.write(config, res.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
