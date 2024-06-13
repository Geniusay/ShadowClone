package io.github.tml.constant;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
public class HealthConstant {
    public static final java.lang.management.OperatingSystemMXBean JAVA_LANG_OS_BEAN = ManagementFactory.getOperatingSystemMXBean();
    public static final OperatingSystemMXBean SUN_OS_BEAN = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    public static final String HEALTH_DATA_COLLECTOR = "healthDataCollector";
}
