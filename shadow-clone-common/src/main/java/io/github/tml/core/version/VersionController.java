package io.github.tml.core.version;

public interface VersionController {

    ShadowCloneVersion currentVersion();

    ShadowCloneVersion nextVersion();

    ShadowCloneVersion upVersion();

    ShadowCloneVersion downVersion();
}
