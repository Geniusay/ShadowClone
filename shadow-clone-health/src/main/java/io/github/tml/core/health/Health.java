package io.github.tml.core.health;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Health {

    private Long totalStorage;

    private Long usableStorage;

    private Long unallocatedStorage;

    private Double systemCpuLoad;

    private Double jvmCpuLoad;

    private Long totalMemory;

    private Long freeMemory;

    private Long maxMemory;

    private Long totalPhysicalMemory;

    private Long freePhysicalMemory;

    private Integer jvmAvailableProcessors;

    private Integer machineAvailableProcessors;

}
