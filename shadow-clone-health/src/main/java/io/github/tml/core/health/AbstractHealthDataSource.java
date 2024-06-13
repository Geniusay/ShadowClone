package io.github.tml.core.health;

/**
 * 健康数量来源抽象层
 * @param <T>
 */
public abstract class AbstractHealthDataSource<T> implements HealthDataSource<T>{

    protected final String healthDataName; //数据名称

    public AbstractHealthDataSource(String healthDataName) {
        this.healthDataName = healthDataName;
    }

    public String getHealthDataName() {
        return healthDataName;
    }
}
