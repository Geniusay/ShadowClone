package io.github.tml.core.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static io.github.tml.constant.ShadowCloneConstant.CONFIG_PREFIX;

// Version information for ShadowClone
@Component
public class ShadowCloneVersion {

    @Value("${"+CONFIG_PREFIX+".version:1.0.0}")
    private String version;

    private String gitSha = "N/A";

    private String buildDate = "N/A";

    private String versionDesc = "N/A";

    public String version() {
        return version;
    }

    public String gitSha() {
        return gitSha;
    }

    public String buildDate() {
        return buildDate;
    }

    public String versionDesc() {
        return versionDesc;
    }
}
