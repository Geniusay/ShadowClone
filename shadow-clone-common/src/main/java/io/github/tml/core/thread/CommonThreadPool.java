package io.github.tml.core.thread;

import java.util.concurrent.*;

public class CommonThreadPool {

    public static ThreadPoolExecutor buildThreadPoolExecutor(ThreadPoolConfig config) {
        return new ThreadPoolExecutor(
                config.getCoreThreads(),
                config.getMaxThreads(),
                config.getKeepAliveTimeMills(),
                TimeUnit.SECONDS,
                config.getQueue(),
                config.getThreadFactory(),
                config.getHandler()
        );
    }

    public static ScheduledExecutorService buildScheduleThreadPoolExecutor(ThreadPoolConfig config){

        return Executors.newScheduledThreadPool(config.getCoreThreads(), config.getThreadFactory());
    }

    public static ExecutorService singleThreadPoolExecutor (){
        return Executors.newSingleThreadExecutor();
    }
}
