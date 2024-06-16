package io.github.tml.config;

import io.github.tml.constant.ShadowCloneConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(ShadowCloneConstant.CONFIG_PREFIX+".health-data-center")
public class HealthDataCenterConfig {

    private long updateHealthFrequencyMills = 5000;
}
