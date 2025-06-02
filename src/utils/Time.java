package utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

public class Time {

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void delay(Runnable action, long delay) {
        delay(action, delay, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> delay(Runnable action, long delay, TimeUnit unit) {
        return scheduler.schedule(action, delay, unit);
    }

    // version loop infinie (tu gardes la ref pour cancel)
    public static ScheduledFuture<?> interval(Runnable action, long periodMillis) {
        return scheduler.scheduleAtFixedRate(action, 0, periodMillis, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> interval(int count, long periodMillis, IntConsumer action) {
        final int[] i = {0};
        ScheduledFuture<?>[] ref = new ScheduledFuture<?>[1];

        ref[0] = scheduler.scheduleAtFixedRate(() -> {
            if (i[0] >= count) {
                ref[0].cancel(false);
                return;
            }
            action.accept(i[0]++);
        }, 0, periodMillis, TimeUnit.MILLISECONDS);

        return ref[0];
    }
}
