package io.github.tml.core.monitor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息反馈
 */
@Slf4j
@Component
public class LogInfoFeedback implements InfoFeedback<String>{

    @Override
    public void feedback(String msg) {
        log.info("update health info:{}",msg);
    }

}
