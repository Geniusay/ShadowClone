package io.github.tml.core.monitor;

import io.github.tml.core.starter.ShadowCloneStarter;
import io.github.tml.core.thread.CommonThreadPool;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static io.github.tml.constant.OrderConstant.HEALTH_ORDER;

@Component
public class HealthInfoMonitor extends ShadowCloneStarter {

    @Resource
    private ApplicationContext applicationContext;


    private List<InfoFeedback> feedbacks;

    private ExecutorService executors = CommonThreadPool.singleThreadPoolExecutor();

    @PostConstruct
    public void init() {
        Map<String, InfoFeedback> beansOfType = applicationContext.getBeansOfType(InfoFeedback.class);
        feedbacks = new ArrayList<>(beansOfType.values());
    }

    public void notify(Map<String,?> map){
        executors.submit(
                ()->{
                    for (InfoFeedback feedback : feedbacks) {
                        feedback.feedback(map);
                    }
                }
        );
    }

    @Override
    public int order() {
        return HEALTH_ORDER+1;
    }
}
