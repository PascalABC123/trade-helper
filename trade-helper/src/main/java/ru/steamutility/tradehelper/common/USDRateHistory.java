package ru.steamutility.tradehelper.common;

import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.Economy;
import ru.steamutility.tradehelper.util.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class USDRateHistory {
    private static final Path file = Path.of(AppPlatform.getApplicationDataFolder() + "\\usd");
    private static final SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    static {
        if (!Files.exists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getUsdRateByDate(Date date) {
        double ans = 0;
        try (var br = new BufferedReader(new FileReader(String.valueOf(file)))) {
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

    public static void requestRecord() {
        final Date now = new Date();
        if(getUsdRateByDate(now) == 0) {
            try {
                String s = String.format("%s -- %s\n", df.format(now), Economy.getUSDRateString());
                Files.write(file, s.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static TreeMap<Date, Double> getSortedRecords() {
        final TreeMap<Date, Double> records = new TreeMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            for(String l : lines) {
                String[] s = l.split(" -- ");
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
