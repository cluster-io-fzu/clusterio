package org.west2.clusterio.namenode.server;


import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.namenode.protocol.BlockInfo;

import java.util.*;

public class BlocksMap {
    private final int capacity;
    //TODO needs replication factor to confirm the length of linkedlist
    private HashMap<Long, LinkedList<BlockInfo>> blocks;
    BlocksMap(int capacity){
        this.capacity = capacity;
        this.blocks = new HashMap<>();
    }

    public boolean insertBlockInfo(long bid,BlockInfo blockInfo){
        if (!blocks.containsKey(bid)){
            return false;
        }
        LinkedList<BlockInfo> infos = blocks.get(bid);
        infos.offer(blockInfo);
        return true;
    }

    public void removeDatanodeBlocks(long blockId,String datanodeUuid){
        if (blocks.containsKey(blockId)){
            LinkedList<BlockInfo> blockInfos = blocks.get(blockId);
            Iterator<BlockInfo> iterator = blockInfos.iterator();
            while (iterator.hasNext()){
                BlockInfo blockInfo = iterator.next();
                if (blockInfo.getDatanodeInfo().getDatanodeUuid().equals(datanodeUuid)){
                    blockInfos.remove(blockInfo);
                }
            }
        }
    }

    public List<BlockInfo> getBlocksByBid(long blockId){
        LinkedList<BlockInfo> infos = blocks.get(blockId);
        return infos;
    }

    public List<String> getRelatedDatanode(long blockId){
        List<BlockInfo> infos = getBlocksByBid(blockId);
        ArrayList<String> uuids = new ArrayList<>(infos.size());
        for (BlockInfo blockInfo :infos) {
            uuids.add(blockInfo.getDatanodeInfo().getDatanodeUuid());
        }
        return uuids;
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
