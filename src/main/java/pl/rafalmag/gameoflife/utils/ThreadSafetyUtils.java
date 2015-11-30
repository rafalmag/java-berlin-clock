package pl.rafalmag.gameoflife.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class ThreadSafetyUtils {

    public static final int MAX_WAIT_TIME_SEC = 10;

    public static void synchronizedAction(Lock lock, Runnable action) {
        try {
            if (lock.tryLock(MAX_WAIT_TIME_SEC, TimeUnit.SECONDS)) {
                try {
                    action.run();
                } finally {
                    lock.unlock();
                }
            } else {
                throw new IllegalStateException("Could not acquire lock");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Could not acquire lock, because of " + e.getMessage(), e);
        }

    }
}
