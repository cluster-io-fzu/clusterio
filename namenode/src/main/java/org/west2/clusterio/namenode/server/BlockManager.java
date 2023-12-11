package org.west2.clusterio.namenode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockManager {
    public static final Logger log = LoggerFactory.getLogger(BlockManager.class);
    protected final BlocksMap blocksMap;

    public BlockManager(){
        blocksMap = new BlocksMap(10);// 2% of the total memory

    }

    public void processFirstReport(){

    }
}
