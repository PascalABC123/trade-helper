package ru.steamutility.tradehelper.common;

import ru.steamutility.tradehelper.AppPlatform;
import ru.steamutility.tradehelper.Economy;
import ru.steamutility.tradehelper.controller.USDRateWindow;
import ru.steamutility.tradehelper.util.Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsdRateHistory {
    private static final File file = new File(AppPlatform.getApplicationDataFolder() + "\\usd");
    private static final SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy --- HH:mm:ss");

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
        String dateString = df.format(date).split(" --- ")[0];
        try (var br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String s = br.readLine();
                String[] res = s.split(" --- ");
                if (dateString.equals(res[0])) {
                    ans = Util.parseDouble(res[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    static {
        USDRateWindow.initChart();
    }

    public static void makeRecord() {
        try (var bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(String.format("\n%s --- %s", df.format(new Date()), Economy.getUSDRateString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
