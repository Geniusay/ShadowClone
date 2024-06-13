package io.github.tml.core.health.aggregator;

import io.github.tml.core.health.AbstractHealthDataSource;
import io.github.tml.core.health.HealthDataCenter;
import io.github.tml.core.health.HealthDataCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static io.github.tml.constant.HealthConstant.HEALTH_DATA_COLLECTOR;

@Slf4j
@Component
@DependsOn(HEALTH_DATA_COLLECTOR)
public class ScheduleAsyncHealthAggregator implements Aggregator {

    private final ThreadPoolExecutor executor;

    private final Map<String, AbstractHealthDataSource<?>> dataSourceMap;

    private final Map<String, Future<Boolean>> runningAggregators;

    private final Map<String, AsyncAggregator> aggregators;

    private final ReentrantLock lock;

    public ScheduleAsyncHealthAggregator() {
        this.dataSourceMap = HealthDataCollector.DataSourceMap();
        this.runningAggregators = new ConcurrentHashMap<>();
        this.aggregators = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
        int dataSourceNum = dataSourceMap.size();
        executor = new ThreadPoolExecutor(
                dataSourceNum
                , dataSourceNum*2
                , 0L
                , TimeUnit.SECONDS
                , new SynchronousQueue<Runnable>()
        );
    }

    public void aggregate() {
        this.dataSourceMap.forEach(
                (name, dataSource) -> {
                    runDataSource(name);
                }
        );
    }

    public void runDataSource(String name){
        try {
            lock.lock();
            AbstractHealthDataSource<?> dataSource = this.dataSourceMap.get(name);
            if(runningAggregators.containsKey(name)){
                return;
            }
            AsyncAggregator asyncAggregator = aggregators.getOrDefault(name,new AsyncAggregator(dataSource));
            Future<Boolean> future = executor.submit(asyncAggregator,true);
            runningAggregators.put(name,future);
            aggregators.putIfAbsent(name,asyncAggregator);
        }finally {
            lock.unlock();
        }
    }

    private void retry(String name) {
        if (runningAggregators.remove(name)!=null) {
            runDataSource(name);
        }
    }

    private synchronized boolean stopAggregator(String name){
        Future<Boolean> future = runningAggregators.get(name);
        if(future!=null){
            future.cancel(true);
            return runningAggregators.remove(name)!=null;
        }
        return false;
    }


    class AsyncAggregator implements Runnable{

        private AbstractHealthDataSource<?> healthDataSource;

        AsyncAggregator(AbstractHealthDataSource<?> healthDataSource) {
            this.healthDataSource = healthDataSource;
        }

        public void run() {
            HealthDataCenter instance = HealthDataCenter.getInstance();
            long updateHealthFrequencyMills = instance.getUpdateHealthFrequencyMills();
            long startTime;
            long sleepTime;
            String healthDataName = healthDataSource.getHealthDataName();
            for (; ;) {
                try {
                    startTime = System.currentTimeMillis();
                    Map<String, ?> healthData = healthDataSource.getHealthData();
                    instance.update(healthData);
                    sleepTime = updateHealthFrequencyMills - (System.currentTimeMillis() - startTime);
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    log.info("{} aggregator stop",healthDataName);
                    return;
                } catch (Exception e){
                    log.error("{} aggregator error:{}", healthDataName, e.getMessage());
                }
            }
        }
    }
}
