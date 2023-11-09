package com.backstreetbrogrammer.loom.model;

import static com.backstreetbrogrammer.loom.util.WaitUtil.waitForGivenSeconds;

public class ClientWallet {
    public static Order validate(final Request request) {
        waitForGivenSeconds(1L, request);

        return new Order();
    }
}
