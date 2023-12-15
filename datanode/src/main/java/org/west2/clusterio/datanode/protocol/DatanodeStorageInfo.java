package org.west2.clusterio.datanode.protocol;

import java.util.LinkedList;

/**
 * One datanode may have multi Storage
 * each storage contains multi blocks
 * but for now we just consider the situation that
 * one datanode only have one storage to simplify the work
 */
public class DatanodeStorageInfo {
    private LinkedList<Long> blocks;

    public DatanodeStorageInfo() {
        this.blocks = new LinkedList<>();
    }

    public LinkedList<Long> getBlocks() {
        return blocks;
    }

    public void addBlock(long blockId){
        blocks.add(blockId);
    }

    public void setBlocks(LinkedList<Long> blocks) {
        this.blocks = blocks;
    }
}
