package org.west2.clusterio.namenode.server;


import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.namenode.protocol.BlockInfo;

import java.util.*;

public class BlocksMap {
    private final int capacity;
    private HashMap<Long, LinkedList<BlockInfo>> blocks;
    BlocksMap(int capacity){
        this.capacity = capacity;
        this.blocks = new HashMap<>();
    }

    public void insertBlockInfo(Block blk,BlockInfo blockInfo){
        LinkedList<BlockInfo> infos = blocks.get(blk);
        infos.offer(blockInfo);
    }

    public List<BlockInfo> getInfosByBid(long blockId){
        LinkedList<BlockInfo> infos = blocks.get(blockId);
        return infos;
    }

    public List<BlockInfo> getInfosFromBlock(Block blk){
        return blocks.get(blk);
    }

    public int getCapacity() {
        return capacity;
    }

    public void close(){
        clear();
        blocks = null;
    }

    public void clear(){
        if (blocks != null){
            blocks.clear();
        }
    }
}
