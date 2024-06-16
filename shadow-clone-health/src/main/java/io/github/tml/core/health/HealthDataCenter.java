package io.github.tml.core.health;

import io.github.tml.config.HealthDataCenterConfig;
import io.github.tml.constant.ShadowCloneConstant;
import io.github.tml.core.health.aggregator.Aggregator;
import io.github.tml.core.health.aggregator.CommonHealthAggregator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class HealthDataCenter {

    @Resource
    HealthDataCenterConfig config;

    private final HealthInfo healthInfo;

    private static volatile HealthDataCenter instance;

    private static ReentrantLock lock = new ReentrantLock();

    private long updateHealthFrequencyMills;

    @Resource(name = "${"+ ShadowCloneConstant.CONFIG_PREFIX +".health-data.aggregator:scheduleAsyncHealthAggregator}")
    Aggregator aggregator;

    @Resource
    CommonHealthAggregator commonHealthAggregator;

    public static HealthDataCenter getInstance(){
        if(instance==null){
            try {
                lock.lock();
                if(instance==null){
                    instance = new HealthDataCenter();
                    return instance;
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @PostConstruct
    private void initEnv(){
        getInstance();
        instance.updateHealthFrequencyMills = config.getUpdateHealthFrequencyMills();
        log.info("start first health data aggregate");
        commonHealthAggregator.aggregate();
        log.info("first health data aggregate end");
        aggregator.aggregate();
    }

    private HealthDataCenter(){
        if(instance!=null){
            throw new RuntimeException("HealthInfoMonitor has been init");
        }
        this.healthInfo = new HealthInfo();
    }

    public <T>  boolean update(String name,Map<String,T> map){
        log.info("{} health data update",name);
        return healthInfo.putInfo(map);
    }

    public long getUpdateHealthFrequencyMills() {
        return this.updateHealthFrequencyMills;
    }
}
