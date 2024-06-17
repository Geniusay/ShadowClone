package io.github.tml.core.monitor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息反馈
 */
@Slf4j
@Component
public class LogInfoFeedback implements InfoFeedback{

    @Override
    public void feedback(Map<String,?> map) {
        log.info("update health info:{}",map);
    }

}
