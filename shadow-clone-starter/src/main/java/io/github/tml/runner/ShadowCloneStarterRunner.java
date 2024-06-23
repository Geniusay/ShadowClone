package io.github.tml.runner;

import io.github.tml.core.starter.ShadowCloneStarter;
import io.github.tml.core.starter.Starter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ShadowCloneStarterRunner implements CommandLineRunner {

    private final List<ShadowCloneStarter> starters;

    private ApplicationContext context;

    public ShadowCloneStarterRunner(ApplicationContext context) {
        this.context = context;
        Map<String, ShadowCloneStarter> starterMap = context.getBeansOfType(ShadowCloneStarter.class);
        starters = starterMap.values().stream().sorted(Comparator.comparingInt(Starter::order)).collect(Collectors.toList());
    }

    @Override
    public void run(String... args){
        try {
            starters.forEach(ShadowCloneStarter::start);
            //TODO 待异步
            starters.forEach(ShadowCloneStarter::afterStart);
        }catch (Exception e){
            log.error("start shadow-clone fail {}",e.getMessage());
            close();
        }
    }

    private void close(){
        int exit = SpringApplication.exit(context, () -> 0);
        System.exit(exit);
    }
}
