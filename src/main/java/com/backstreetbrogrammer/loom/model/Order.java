package com.backstreetbrogrammer.loom.model;

import static com.backstreetbrogrammer.loom.util.WaitUtil.waitForGivenSeconds;

public class Order {
    public Order() {
    }

    public Order(final Request request) {
        waitForGivenSeconds(1L, request);
    }

    public Order validate(final Order validatedOrder) {
        // validation logic...
        return validatedOrder;
    }

    public Order enrich(final Order enrichedOrder) {
        // enrichment logic...
        return enrichedOrder;
    }

    public Order persist(final Order persistedOrder) {
        // persistence logic...
        return persistedOrder;
    }

    public void sendToDownstream() {
        // connection logic to downstream...
        waitForGivenSeconds(1L);
    }
}
