package com.backstreetbrogrammer.loom.model;

import static com.backstreetbrogrammer.loom.util.WaitUtil.waitForGivenSeconds;

public class MarketData {
    public static Order enrich(final Request request) {
        waitForGivenSeconds(1L, request);

        return new Order();
    }
}
