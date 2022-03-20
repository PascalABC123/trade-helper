package ru.steamutility.tradehelper.common;

import javafx.util.Pair;
import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.util.Util;

import java.io.*;
import java.util.Objects;

public class Config {
    private static File config = new File(AppPlatform.getConfigPath());
    private static boolean unset = false;

    public static boolean isUnset() {
        return unset;
    }

    static {
        if (!config.exists()) {
            unset = true;
            try {
                config.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    public static String getProperty(String property) {
        String value = null;
        try(BufferedReader br = new BufferedReader(new FileReader(config))) {
            String s;
            while((s = br.readLine()) != null) {
                Pair<String, String> res = Util.getPropertyPair(s);
                if(Objects.requireNonNull(res).getKey().equalsIgnoreCase(property)) {
                    value = res.getValue();
                    break;
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

    public static void setProperty(String property, String value) {
        unset = false;
        boolean found = false;
        File temp = new File(AppPlatform.getConfigPath() + ".tmp");
        try(var br = new BufferedReader(new FileReader(config));
            var bw = new BufferedWriter(new FileWriter(temp))) {
            temp.createNewFile();
            while(br.ready()) {
                String s = br.readLine();
                var p = Util.getPropertyPair(s);
                if(p.getKey().equalsIgnoreCase(property)) {
                    found = true;
                    s = Util.replacePropertyValue(s, value);
                }
                bw.write(s);
                bw.newLine();
            }
            if(!found) {
                bw.write(Util.makePropertyPair(property, value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.delete();
        temp.renameTo(new File(AppPlatform.getConfigPath()));
    }
}
