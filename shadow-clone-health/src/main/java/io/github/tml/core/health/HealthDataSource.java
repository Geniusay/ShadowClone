package io.github.tml.core.health;

import java.util.Map;

public interface HealthDataSource<T> {

    Map<String, T> getHealthData();

}
