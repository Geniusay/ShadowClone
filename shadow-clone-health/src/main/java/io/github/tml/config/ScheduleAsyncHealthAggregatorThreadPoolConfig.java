package io.github.tml.config;


import io.github.tml.constant.ShadowCloneConstant;
import io.github.tml.core.health.HealthDataCollector;
import io.github.tml.core.thread.ThreadPoolConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

@Data
@Component
@ConfigurationProperties(prefix = ShadowCloneConstant.CONFIG_PREFIX+".schedule-aggregator.thread-pool")
public class ScheduleAsyncHealthAggregatorThreadPoolConfig extends ThreadPoolConfig {

    private Integer coreThreads;

    private Integer maxThreads;

    private Long keepAliveTimeMills = 0L;

    private BlockingQueue queue = new SynchronousQueue<Runnable>();

    @Autowired
    public ScheduleAsyncHealthAggregatorThreadPoolConfig(HealthDataCollector collector) {
        this.coreThreads = collector.DataSourceMap().size();
        this.maxThreads = coreThreads*2;
    }

}
