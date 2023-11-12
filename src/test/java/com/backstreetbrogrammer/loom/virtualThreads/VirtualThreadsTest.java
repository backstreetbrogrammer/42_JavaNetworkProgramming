package com.backstreetbrogrammer.loom.virtualThreads;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class VirtualThreadsTest {

    @Test
    @DisplayName("Test platform thread using Thread constructor")
    void testPlatformThreadUsingThreadConstructor() throws InterruptedException {
        final var platformThread = new Thread(() -> System.out.printf("I am running inside thread=%s%n",
                                                                      Thread.currentThread()));
        platformThread.start();
        platformThread.join();
    }

    @Test
    @DisplayName("Test platform thread using Thread.ofPlatform() method")
    void testPlatformThreadUsingThreadOfPlatformMethod() throws InterruptedException {
        final var platformThread = Thread.ofPlatform().unstarted(() ->
                                                                         System.out.printf("I am running inside thread=%s%n",
                                                                                           Thread.currentThread()));
        platformThread.start();
        platformThread.join();
    }

    @Test
    @DisplayName("Test virtual thread using Thread.ofVirtual() method")
    void testVirtualThreadUsingThreadOfVirtualMethod() throws InterruptedException {
        final var virtualThread = Thread.ofVirtual().unstarted(() ->
                                                                       System.out.printf("I am running inside thread=%s%n",
                                                                                         Thread.currentThread()));
        virtualThread.start();
        virtualThread.join();
    }

    @Test
    @DisplayName("Test multiple virtual threads")
    void testMultipleVirtualThreads() throws InterruptedException {
        final Runnable runnable = () -> System.out.printf("I am running inside thread=%s%n",
                                                          Thread.currentThread());
        final List<Thread> virtualThreads = new ArrayList<>();
        for (var i = 0; i < 3; i++) {
            virtualThreads.add(Thread.ofVirtual().unstarted(runnable));
        }
        for (final var virtualThread : virtualThreads) {
            virtualThread.start();
        }
        for (final var virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }

    @Test
    @DisplayName("Test large number of virtual threads")
    void testLargeNumberOfVirtualThreads() {
        try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    TimeUnit.SECONDS.sleep(1L);
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
    }

}
