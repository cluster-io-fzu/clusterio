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
    private final DatanodeManager manager;
    protected final BlocksMap blocksMap;

    public BlockManager(final NameSystem sys){
        this.sys = sys;
        blocksMap = new BlocksMap(10);// 2% of the total memory
        manager = sys.getDatanodeManager();
    }

    public void processFirstReport(DatanodeRegistration reg, StorageBlockReport[] reports){
        String datanodeUuid = reg.getDatanodeUuid();
        DatanodeInfo datanodeInfo = manager.getRegistry().get(datanodeUuid);
        DatanodeStorageInfo storageInfo = datanodeInfo.getStorageInfo();
        for (StorageBlockReport report : reports) {
            Block[] blocks = report.getBlocks();
            datanodeInfo.setNumBlocks((int)report.getNumberOfBlocks());
            for (Block block :blocks) {
                BlockInfo blockInfo = new BlockInfo(block);
                blockInfo.setInfo(datanodeInfo);
                storageInfo.addBlock(block.getBlockId());
                boolean success =  blocksMap.insertBlockInfo(block.getBlockId(),blockInfo);
                if (!success){
                    log.warn("No related block in the manager");
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
