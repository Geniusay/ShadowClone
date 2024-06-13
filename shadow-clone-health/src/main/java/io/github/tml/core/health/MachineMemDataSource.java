package io.github.tml.core.health;

import org.springframework.stereotype.Component;
import java.util.Map;
import static io.github.tml.constant.HealthConstant.SUN_OS_BEAN;

@Component
public class MachineMemDataSource extends AbstractHealthDataSource<Long> {


    public MachineMemDataSource() {
        super("Machine_Memory");
    }

    @Override
    public Map<String, Long> getHealthData() {
        return Map.of(
                HealthInfoFieldName.TOTAL_PHY_MEMORY,SUN_OS_BEAN.getTotalPhysicalMemorySize(),
                HealthInfoFieldName.FREE_PHY_MEMORY, SUN_OS_BEAN.getFreePhysicalMemorySize()
        );
    }

}
