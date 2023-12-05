package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.HdfsProtos;

public class StorageReport {
    private String storageUuid;
    private boolean failed;
    private long capacity;
    private long used;
    private long remaining;
    private long blockPoolUsed;

    public StorageReport(StorageReport from){
        this(from.storageUuid,from.failed,from.capacity,
                from.used,from.remaining,from.blockPoolUsed);
    }

    public StorageReport(String storageUuid, boolean failed, long capacity, long used, long remaining, long blockPoolUsed) {
        this.storageUuid = storageUuid;
        this.failed = failed;
        this.capacity = capacity;
        this.used = used;
        this.remaining = remaining;
        this.blockPoolUsed = blockPoolUsed;
    }

    public HdfsProtos.StorageReportProto parse(){
        return HdfsProtos.StorageReportProto.newBuilder()
                .setStorageUuid(storageUuid).setFailed(failed)
                .setCapacity(capacity).setUsed(used).setRemaining(remaining)
                .setBlockPoolUsed(blockPoolUsed).build();
    }

    public String getStorageUuid() {
        return storageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        this.storageUuid = storageUuid;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public long getBlockPoolUsed() {
        return blockPoolUsed;
    }

    public void setBlockPoolUsed(long blockPoolUsed) {
        this.blockPoolUsed = blockPoolUsed;
    }
}
