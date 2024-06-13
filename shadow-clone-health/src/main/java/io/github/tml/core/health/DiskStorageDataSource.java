package io.github.tml.core.health;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;

@Component
public class DiskStorageDataSource extends AbstractHealthDataSource<Long> {

    public DiskStorageDataSource() {
        super("DISK");

    }

    @Override
    public Map<String, Long> getHealthData() {
        long totalSpace = 0;
        long usableSpace = 0;
        long unallocatedSpace = 0;
        for (Path root : FileSystems.getDefault().getRootDirectories()) {
            try {
                FileStore store = java.nio.file.Files.getFileStore(root);
                totalSpace += store.getTotalSpace(); // 总空间
                usableSpace += store.getUsableSpace(); // 可用空间
                unallocatedSpace += store.getUnallocatedSpace(); // 未分配空间
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Map.of(
                HealthInfoFieldName.TOTAL_STORAGE,totalSpace,
                HealthInfoFieldName.USABLE_STORAGE,usableSpace,
                HealthInfoFieldName.UNALLOCATED_STORAGE,unallocatedSpace
        );
    }
}
