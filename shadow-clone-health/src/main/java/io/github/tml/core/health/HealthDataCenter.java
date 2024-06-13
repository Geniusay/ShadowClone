package io.github.tml.core.health;

import io.github.tml.core.health.aggregator.ScheduleAsyncHealthAggregator;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import static io.github.tml.constant.HealthConstant.HEALTH_DATA_COLLECTOR;

@Component
@DependsOn(HEALTH_DATA_COLLECTOR)
public class HealthDataCenter {

    private final HealthInfo healthInfo;

    private static volatile HealthDataCenter instance;

    private static ReentrantLock lock = new ReentrantLock();

    private long updateHealthFrequencyMills = 5000;

    @Resource
    ScheduleAsyncHealthAggregator aggregator;

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
    public void start(){
        aggregator.aggregate();
    }
    public HealthDataCenter(){
        if(instance!=null){
            throw new RuntimeException("HealthInfoMonitor has been init");
        }
        healthInfo = new HealthInfo();
    }

    public <T>  boolean update(String key,T value){
        return healthInfo.putInfo(key,value);
    }

    public <T>  boolean update(Map<String,T> map){
        return healthInfo.putInfo(map);
    }

    public long getUpdateHealthFrequencyMills() {
        return updateHealthFrequencyMills;
    }
}
