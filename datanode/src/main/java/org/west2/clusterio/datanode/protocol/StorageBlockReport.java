package org.west2.clusterio.datanode.protocol;

public class StorageBlockReport {
    private String storageUuid;
    private long[] blocks;
    private long numberOfBlocks;
    private byte[] blocksBuffers;

    public StorageBlockReport(String storageUuid, long[] blocks, long numberOfBlocks, byte[] blocksBuffers) {
        this.storageUuid = storageUuid;
        this.blocks = blocks;
        this.numberOfBlocks = numberOfBlocks;
        this.blocksBuffers = blocksBuffers;
    }

    public String getStorageUuid() {
        return storageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        this.storageUuid = storageUuid;
    }

    public long[] getBlocks() {
        return blocks;
    }

    public void setBlocks(long[] blocks) {
        this.blocks = blocks;
    }

    public long getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public void setNumberOfBlocks(long numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }

    public byte[] getBlocksBuffers() {
        return blocksBuffers;
    }

    public void setBlocksBuffers(byte[] blocksBuffers) {
        this.blocksBuffers = blocksBuffers;
    }
}
