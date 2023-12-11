package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.namenode.protocol.BlockInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BlocksMap {
    public static class DatanodeIterator implements Iterator<DatanodeInfo>{
        private final BlockInfo blockInfo;
        private int nextIdx = 0;

        DatanodeIterator(BlockInfo info){
            this.blockInfo = info;
        }

        @Override
        public boolean hasNext() {
            if (blockInfo == null){
                return false;
            }
            while (nextIdx < blockInfo.getCapacity() &&
                    blockInfo.getDatanodeInfo(nextIdx) == null){
                nextIdx++;
            }
            return nextIdx < blockInfo.getCapacity();
        }

        @Override
        public DatanodeInfo next() {
            return blockInfo.getDatanodeInfo(nextIdx++);
        }

    }
//    private final BlockInfo blockInfo;
//    private int nextIdx = 0;
    private final int capacity;
    private Map<Block,BlockInfo> blocks;

    public BlocksMap(int capacity){
        this.capacity = capacity;
        this.blocks = new HashMap<>();
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
