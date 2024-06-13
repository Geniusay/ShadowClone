package io.github.tml.core.health;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JvmMemDataSource extends AbstractHealthDataSource<Long> {

    public JvmMemDataSource() {
        super("JVM_Memory");
    }

    @Override
    public Map<String, Long> getHealthData() {
        Runtime runtime = Runtime.getRuntime();
        return Map.of(
                HealthInfoFieldName.TOTAL_MEMORY,runtime.totalMemory(),
                HealthInfoFieldName.FREE_MEMORY,runtime.freeMemory(),
                HealthInfoFieldName.MAX_MEMORY,runtime.maxMemory()
        );

    }
}
