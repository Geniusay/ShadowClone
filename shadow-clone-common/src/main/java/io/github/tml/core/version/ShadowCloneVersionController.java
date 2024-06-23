package io.github.tml.core.version;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ShadowCloneVersionController implements VersionController{

    @Resource
    ShadowCloneVersion version;

    @Override
    public ShadowCloneVersion currentVersion() {
        return version;
    }

    @Override
    public ShadowCloneVersion nextVersion() {
        return null;
    }

    @Override
    public ShadowCloneVersion upVersion() {
        return null;
    }

    @Override
    public ShadowCloneVersion downVersion() {
        return null;
    }
}
