package org.west2.clusterio.namenode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;
import org.west2.clusterio.datanode.protocol.StorageBlockReport;
import org.west2.clusterio.namenode.protocol.BlockInfo;


public class BlockManager {
    public static final Logger log = LoggerFactory.getLogger(BlockManager.class);
    private final DatanodeManager manager;
    protected final BlocksMap blocksMap;

    public BlockManager(){
        blocksMap = new BlocksMap(10);// 2% of the total memory
        manager = DatanodeManager.getManager();
    }

    public void processFirstReport(DatanodeRegistration reg, StorageBlockReport[] reports){
        String datanodeUuid = reg.getDatanodeUuid();
        DatanodeInfo datanodeInfo = manager.getRegistry().get(datanodeUuid);
        for (StorageBlockReport report : reports) {
            Block[] blocks = report.getBlocks();
            datanodeInfo.setNumBlocks((int)report.getNumberOfBlocks());
            for (Block block :blocks) {
                BlockInfo blockInfo = new BlockInfo(block);
                blockInfo.setInfo(datanodeInfo);
                boolean success =  blocksMap.insertBlockInfo(block.getBlockId(),blockInfo);
                if (!success){
                    log.warn("No related block in the manager");
                }
            }
        }
    }
    //remove all related blocks when a datanode is down
    public void removeDatanodeBlocks(long blockId,String datanodeUuid){
        blocksMap.removeDatanodeBlocks(blockId,datanodeUuid);
    }
}
