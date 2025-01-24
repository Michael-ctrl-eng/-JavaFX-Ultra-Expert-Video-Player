```java
package com.example.videoplayer.framework.utils;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadManager class to manage thread executions
 */
public class ThreadManager {

    private final ExecutorService backgroundExecutor;
    private final Scheduler backgroundScheduler;

    /**
     * Constructor for the ThreadManager class
     */
    public ThreadManager() {
        backgroundExecutor = Executors.newFixedThreadPool(10);
        backgroundScheduler = Schedulers.from(backgroundExecutor);
    }

    /**
     * Gets the Background Scheduler
     * @return Scheduler Instance
     */
    public Scheduler getBackgroundScheduler() {
        return backgroundScheduler;
    }

    /**
     * Shutdown all the resources of the thread manager
     */
    public void shutdown() {
        backgroundExecutor.shutdown();
        try {
            if (!backgroundExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                backgroundExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            backgroundExecutor.shutdownNow();
        }
    }
}
```
