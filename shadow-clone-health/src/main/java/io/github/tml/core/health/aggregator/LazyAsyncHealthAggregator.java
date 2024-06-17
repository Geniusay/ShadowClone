package io.github.tml.core.health.aggregator;

import io.github.tml.config.LazyAsyncHealthAggregatorThreadPoolConfig;
import io.github.tml.core.health.HealthDataCenter;
import io.github.tml.core.thread.CommonThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class LazyAsyncHealthAggregator extends AsyncAggregator<LazyAsyncHealthAggregatorThreadPoolConfig> {


    private final ScheduledExecutorService scheduledExecutor;

    private final ThreadPoolExecutor executor;

    private Map<String, AtomicLong> updateTimeStampMap;

    @Autowired
    public LazyAsyncHealthAggregator(LazyAsyncHealthAggregatorThreadPoolConfig config) {
        super(config);
        this.scheduledExecutor = Executors.newScheduledThreadPool(1);
        this.executor = CommonThreadPool.buildThreadPoolExecutor(config);
        this.updateTimeStampMap = new ConcurrentHashMap<>();
    }

    @Override
    public void aggregate() {

        this.dataSourceMap.forEach((name,dataSource)->{
            updateTimeStampMap.put(name,new AtomicLong(currentTime()));
        });
        long frequencyMills = HealthDataCenter.getInstance().getUpdateHealthFrequencyMills();
        this.scheduledExecutor.scheduleWithFixedDelay(
                ()->this.dataSourceMap.forEach((name, dataSource) -> {
                            executor.submit(() -> {
                                long timestamp = currentTime();
                                Map<String, ?> healthData = dataSource.getHealthData();
                                if (!checkUpdateTimeStamp(name,timestamp)) {
                                    log.error("The {} data acquisition this time is not the latest",name);
                                    return;
                                }
                                HealthDataCenter.getInstance().update(name,healthData);
                            });
                        }
                ),0,frequencyMills,TimeUnit.MILLISECONDS
        );
    }

    /**
     * 检查更新时间戳，防止线程池中，旧任务更新数据替换新任务数据
     * @param name
     * @param timestamp
     * @return
     */
    private boolean checkUpdateTimeStamp(String name,long timestamp){
        AtomicLong oldTimestamp = updateTimeStampMap.get(name);
        long oldTime = oldTimestamp.get();

        if (oldTime>=timestamp) {
            return false;
        }

        while (!oldTimestamp.compareAndSet(oldTime, timestamp)) {
            oldTime = oldTimestamp.get();
            if(oldTime >=timestamp){
                return false;
            }
        }
        return true;
    }

    private long currentTime(){
        return System.currentTimeMillis();
    }
}
