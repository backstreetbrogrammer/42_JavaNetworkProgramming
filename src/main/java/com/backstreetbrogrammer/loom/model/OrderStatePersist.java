package com.backstreetbrogrammer.loom.model;

import static com.backstreetbrogrammer.loom.util.WaitUtil.waitForGivenSeconds;

public class OrderStatePersist {
    public static Order persist(final Request request) {
        waitForGivenSeconds(1L, request);

        return new Order();
    }
}
