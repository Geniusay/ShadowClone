package io.github.tml.config;

import io.github.tml.constant.ShadowCloneConstant;
import io.github.tml.core.thread.ThreadPoolConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Data
@Component
@ConfigurationProperties(prefix = ShadowCloneConstant.CONFIG_PREFIX+".lazy-aggregator.thread-pool")
public class LazyAsyncHealthAggregatorThreadPoolConfig extends ThreadPoolConfig {
    private Integer coreThreads = 2;

    private Integer maxThreads = 4;

    private Long keepAliveTimeMills = 60000L;

    private BlockingQueue queue = new ArrayBlockingQueue<Runnable>(1024);

}
