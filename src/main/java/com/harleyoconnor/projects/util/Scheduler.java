package com.harleyoconnor.projects.util;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Harley O'Connor
 */
public final class Scheduler {

    private Scheduler() {}

    public static void schedule(final Runnable runnable, final Duration period) {
        schedule(runnable, 0, period.toMillis());
    }

    public static void schedule(final Runnable runnable, final Duration delay, final Duration period) {
        schedule(runnable, delay.toMillis(), period.toMillis());
    }

    public static void schedule(final Runnable runnable, final long delay, final long period) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay, period);
    }

}
