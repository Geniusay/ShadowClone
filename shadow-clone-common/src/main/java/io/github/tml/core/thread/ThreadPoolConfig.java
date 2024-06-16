package io.github.tml.core.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

@Data
@Configuration
@ConfigurationProperties(prefix = "shadow-clone.thread-pool")
public class ThreadPoolConfig {

    private Integer coreThreads;

    private Integer maxThreads;

    private Long keepAliveTimeMills;

    @Resource(name = "commonThreadFactory")
    private ThreadFactory threadFactory;

    private BlockingQueue queue;

    private RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

}
