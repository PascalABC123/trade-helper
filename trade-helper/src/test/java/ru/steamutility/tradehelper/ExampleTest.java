package ru.steamutility.tradehelper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.steamutility.tradehelper.common.USDRateHistory;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

    @Test
    @DisplayName("key is valid")
    void test1() {
        assertEquals(USDRateHistory.getUsdRateByDate(new Date()), 104.12);
    }

    @Test
    @DisplayName("key is not valid")
    void test2() {

    }
}
