package io.github.tml.core.health;

import org.springframework.stereotype.Component;
import java.util.Map;
import static io.github.tml.constant.HealthConstant.JAVA_LANG_OS_BEAN;

@Component
public class ProcessorDataSource extends AbstractHealthDataSource<Integer>{


    public ProcessorDataSource() {
        super("Processor");
    }

    @Override
    public Map<String, Integer> getHealthData() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return Map.of(
                HealthInfoFieldName.JVM_AVAILABLE_PROCESSOR,availableProcessors,
                HealthInfoFieldName.MACHINE_AVAILABLE_PROCESSOR,JAVA_LANG_OS_BEAN.getAvailableProcessors()
        );
    }
}
