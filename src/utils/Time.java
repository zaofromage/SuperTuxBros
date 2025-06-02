package utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Time {

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void delay(Runnable action, long delay) {
        delay(action, delay, TimeUnit.MILLISECONDS);
    }

    public static void delay(Runnable action, long delay, TimeUnit unit) {
        scheduler.schedule(action, delay, unit);
    }
}
