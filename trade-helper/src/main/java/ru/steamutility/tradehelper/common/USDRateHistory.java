package ru.steamutility.tradehelper.common;

import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.Economy;
import ru.steamutility.tradehelper.controller.USDRateWindow;
import ru.steamutility.tradehelper.util.Util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class USDRateHistory {
    private static final File file = new File(AppPlatform.getApplicationDataFolder() + "\\usd");
    private static final SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    static {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getUsdRateByDate(Date date) {
        double ans = 0;
        try (var br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String[] res = br.readLine().split(" -- ");
                if(res.length == 2) {
                    Date d = df.parse(res[0]);
                    if (Util.areDatesEqualByDay(date, d)) {
                        ans = Util.parseDouble(res[1]);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static void makeRecord() {
        try (var bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(String.format("\n%s -- %s", df.format(new Date()), Economy.getUSDRateString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TreeMap<Date, Double> getUniqueRecords() {
        final TreeMap<Date, Double> records = new TreeMap<>();
        try (var br = new BufferedReader(new FileReader(file))) {
            while(br.ready()) {
                String[] s = br.readLine().split(" -- ");
                if (s.length == 2) {
                    Date date = df.parse(s[0]);
                    date.setHours(0);
                    date.setMinutes(0);
                    date.setSeconds(0);
                    records.put(date, Util.parseDouble(s[1]));
                }
            }
        } catch (IOException | ParseException e ) {
            e.printStackTrace();
        }
        return records;
    }
}
