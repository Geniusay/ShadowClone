package io.github.tml.core.health;

import io.github.tml.core.FinalWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component
public class HealthDataCollector{

    @Resource
    private ApplicationContext applicationContext;
    /**
     * 不可变，初始化后不可放置的数据源Map
     */
    private FinalWrapper<Map<String, AbstractHealthDataSource<?>>> dataSourceMap;

    @PostConstruct
    private void init(){
        Map<String, AbstractHealthDataSource> dataSourceBeans = applicationContext.getBeansOfType(AbstractHealthDataSource.class);
        HashMap<String,AbstractHealthDataSource<?>> temp = new HashMap<>();
        log.info("find {} health data sources",dataSourceBeans.size());
        dataSourceBeans.forEach(
                (k,v)->{
                    temp.put(v.getHealthDataName(),v);
                }
        );
        dataSourceMap = new FinalWrapper<>(Collections.unmodifiableMap(temp));
    }

    public Map<String, AbstractHealthDataSource<?>> DataSourceMap(){
        return dataSourceMap.getValue();
    }
}
