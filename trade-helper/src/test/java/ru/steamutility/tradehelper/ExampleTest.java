package ru.steamutility.tradehelper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

    @Test
    @DisplayName("key is valid")
    void test1() {
        for(int i = 0; i < 10; i++) {
            assertEquals(CSGOMarketApiClient.isKeyValid("ZAOoS96dGU51j951u6v6b8k6LYBV7X9"), true);
        }
    }

    @Test
    @DisplayName("key is not valid")
    void test2() {
        for(int i = 0; i < 10; i++) {
            assertEquals(CSGOMarketApiClient.isKeyValid("ZAOoS96dGU51j951u66b8k6LYBV7X9"), false);
        }
    }
}
