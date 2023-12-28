package org.west2.clusterio.namenode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;
import org.west2.clusterio.datanode.protocol.DatanodeStorageInfo;
import org.west2.clusterio.datanode.protocol.StorageBlockReport;
import org.west2.clusterio.namenode.protocol.BlockInfo;

import java.util.Iterator;


public class BlockManager {
    public static final Logger log = LoggerFactory.getLogger(BlockManager.class);
    private final NameSystem sys;
    protected final BlocksMap blocksMap;
    private DatanodeManager manager;

    public BlockManager(final NameSystem system){
        this.sys = system;
        blocksMap = new BlocksMap(10);// 2% of the total memory
    }

    public void processFirstReport(DatanodeRegistration reg, StorageBlockReport[] reports){
        if (manager == null){
            manager = sys.getDatanodeManager();
        }
        String datanodeUuid = reg.getDatanodeUuid();
        DatanodeInfo datanodeInfo = manager.getRegistry().get(datanodeUuid);
        DatanodeStorageInfo storageInfo = datanodeInfo.getStorageInfo();
        for (StorageBlockReport report : reports) {
            Block[] blocks = report.getBlocks();
            //Set number of blocks in this dn
            datanodeInfo.setNumBlocks((int)report.getNumberOfBlocks());
            for (Block block :blocks) {
                BlockInfo blockInfo = new BlockInfo(block);
                blockInfo.setInfo(datanodeInfo);
                storageInfo.addBlock(block.getBlockId());
                boolean success =  blocksMap.insertBlockInfo(block.getBlockId(),blockInfo);
                if (!success){
                    log.warn("block-{} is not in the maps",block.getBlockId());
                }
            }
        }
    }
    //remove all related blocks when a datanode is down
    public void removeDatanodeBlocks(String datanodeUuid){
        DatanodeStorageInfo storageInfo = manager.getRegistry().get(datanodeUuid).getStorageInfo();
        Iterator<Long> iterator = storageInfo.getBlocks().iterator();
        while (iterator.hasNext()){
            blocksMap.removeDatanodeBlocks(iterator.next(),datanodeUuid);
        }
    }
}
