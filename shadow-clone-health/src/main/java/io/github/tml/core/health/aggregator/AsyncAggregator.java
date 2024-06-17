package io.github.tml.core.health.aggregator;


import io.github.tml.core.thread.CommonThreadPool;
import io.github.tml.core.thread.ThreadPoolConfig;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AsyncAggregator<T extends ThreadPoolConfig> extends AbstractAggregator{

    protected ThreadPoolExecutor executor;

    public AsyncAggregator(T config) {
        this.executor = CommonThreadPool.buildThreadPoolExecutor(config);
    }
}
