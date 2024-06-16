package io.github.tml.core.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CommonThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private static final String namePrefix = "[shadow-clone]";


    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, namePrefix + "-thread-" + threadNumber.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
