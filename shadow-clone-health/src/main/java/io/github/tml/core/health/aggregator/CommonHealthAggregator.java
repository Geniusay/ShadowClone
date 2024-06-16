package io.github.tml.core.health.aggregator;

import io.github.tml.core.health.HealthDataCenter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonHealthAggregator extends AbstractAggregator {

    @Override
    public void aggregate() {
        this.dataSourceMap.forEach((name, dataSource) -> {
                    Map<String, ?> healthData = dataSource.getHealthData();
                    HealthDataCenter.getInstance().update(name, healthData);
                }
        );
    }
}
