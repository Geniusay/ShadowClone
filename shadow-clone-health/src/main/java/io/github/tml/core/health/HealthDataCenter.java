package io.github.tml.core.health;

import io.github.tml.config.HealthDataCenterConfig;
import io.github.tml.constant.ShadowCloneConstant;
import io.github.tml.core.health.aggregator.AsyncAggregator;
import io.github.tml.core.health.aggregator.CommonHealthAggregator;
import io.github.tml.core.monitor.HealthInfoMonitor;
import io.github.tml.core.starter.ShadowCloneStarter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static io.github.tml.constant.OrderConstant.HEALTH_ORDER;

@Slf4j
@Component
public class HealthDataCenter extends ShadowCloneStarter {

    private static volatile HealthDataCenter instance;

    private static final ReentrantLock lock = new ReentrantLock();

    private final HealthInfo healthInfo;

    @Resource
    private HealthDataCenterConfig config;

    @Resource
    private HealthInfoMonitor monitor;

    @Resource(name = "${"+ ShadowCloneConstant.CONFIG_PREFIX +".health-data.aggregator:scheduleAsyncHealthAggregator}")
    private AsyncAggregator<?> aggregator;

    @Resource
    private CommonHealthAggregator commonHealthAggregator;


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

    private void initEnv(){
        getInstance();
        initProtectedEnv();
        log.info("start first health data aggregate");
        commonHealthAggregator.aggregate();
        log.info("first health data aggregate end");
    }

    private void initProtectedEnv(){
        instance.config = this.config;
        instance.monitor = this.monitor;
    }

    private HealthDataCenter(){
        if(instance!=null){
            throw new RuntimeException("HealthInfoMonitor has been init");
        }
        this.healthInfo = new HealthInfo();
    }

    public <T>  boolean update(String name,Map<String,T> map){
        boolean res = healthInfo.putInfo(map);
        monitor.notify(map);
        return res;
    }

    public long getUpdateHealthFrequencyMills() {
        return this.config.getUpdateHealthFrequencyMills();
    }

    @Override
    public void start() {
        initEnv();
    }

    @Override
    public void afterStart(){
        log.info("start health data async aggregate");
        aggregator.aggregate();
    }

    @Override
    public int order() {
        return HEALTH_ORDER+1;
    }
}
