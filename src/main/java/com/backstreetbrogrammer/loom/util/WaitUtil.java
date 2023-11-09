package com.backstreetbrogrammer.loom.util;

import com.backstreetbrogrammer.loom.model.Request;

import java.util.concurrent.TimeUnit;

public class WaitUtil {

    private WaitUtil() {
    }

    public static void waitForGivenSeconds(final long waitSeconds, final Request... request) {
        try {
            TimeUnit.SECONDS.sleep(waitSeconds);
        } catch (final InterruptedException e) {
            if (request != null && request.length > 0) {
                System.err.println(request[0]);
            }
            throw new RuntimeException(e);
        }
    }

}
