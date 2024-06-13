package io.github.tml.core.health;

import org.springframework.stereotype.Component;

import java.util.Map;

import static io.github.tml.constant.HealthConstant.SUN_OS_BEAN;

@Component
public class CpuDataSource extends AbstractHealthDataSource<Double>{

    public CpuDataSource() {
        super("CPU");
    }

    @Override
    public Map<String, Double> getHealthData() {
        return Map.of(
                HealthInfoFieldName.SYSTEM_CPU_LOAD,SUN_OS_BEAN.getSystemCpuLoad(),
                HealthInfoFieldName.JVM_CPU_LOAD,SUN_OS_BEAN.getProcessCpuLoad()
        );
    }
}
