package io.github.tml.starter;

import io.github.tml.core.starter.ShadowCloneStarter;
import io.github.tml.core.version.ShadowCloneVersion;
import io.github.tml.core.version.ShadowCloneVersionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import static io.github.tml.constant.OrderConstant.VERSION_ORDER;

/**
 * TODO 等待完成版本校验功能
 */
@Slf4j
@Component
public class VersionCheckStarter extends ShadowCloneStarter {

    @Resource
    ShadowCloneVersionController shadowCloneVersionController;

    @Override
    public void start() {
        ShadowCloneVersion version = shadowCloneVersionController.currentVersion();
        log.info("current shadow-clone version is {}",version.version());
    }

    @Override
    public int order() {
        return VERSION_ORDER;
    }
}
