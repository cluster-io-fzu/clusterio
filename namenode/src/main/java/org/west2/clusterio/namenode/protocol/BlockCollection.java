package org.west2.clusterio.namenode.protocol;

import org.west2.clusterio.common.protocol.Block;

public interface BlockCollection {
    /**
     * Get the number of blocks
     */
    int numBlocks();
    /**
     * Get the blocks
     * @return
     */
    Block[] getBlocks();
    /**
     * Set the block into the list
     */
    void setBlock(int index, Block blk);
    /**
     * get the id of block collection
     */
    long getId();
}
