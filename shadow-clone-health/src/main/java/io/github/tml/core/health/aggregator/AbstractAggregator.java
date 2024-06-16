package io.github.tml.core.health.aggregator;


import io.github.tml.core.health.AbstractHealthDataSource;
import io.github.tml.core.health.HealthDataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Component
public abstract class AbstractAggregator implements Aggregator{

    protected Map<String, AbstractHealthDataSource<?>> dataSourceMap;

    @Resource
    private HealthDataCollector collector;

    public AbstractAggregator() {
    }

    @PostConstruct
    public void initDataSourceMap(){
        this.dataSourceMap = collector.DataSourceMap();
    }
}
