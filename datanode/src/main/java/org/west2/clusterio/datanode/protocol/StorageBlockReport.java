package org.west2.clusterio.datanode.protocol;

import com.google.protobuf.ByteString;
import org.west2.clusterio.common.protocol.Block;

public class StorageBlockReport {
    private String storageUuid;
    private Block[] blocks;
    private long numberOfBlocks;
//    private ByteString[] blocksBuffers;


    public StorageBlockReport(String storageUuid, Block[] blocks, long numberOfBlocks) {
        this.storageUuid = storageUuid;
        this.blocks = blocks;
        this.numberOfBlocks = numberOfBlocks;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public String getStorageUuid() {
        return storageUuid;
    }

    public void setStorageUuid(String storageUuid) {
        this.storageUuid = storageUuid;
    }



    public long getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public void setNumberOfBlocks(long numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }


}
