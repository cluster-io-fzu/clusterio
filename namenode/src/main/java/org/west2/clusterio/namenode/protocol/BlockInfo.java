package org.west2.clusterio.namenode.protocol;

import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;

public class BlockInfo extends Block {
    public static final BlockInfo[] EMPTY_ARRAY = {};

    /**
     * Replication factor
     */
    private short replication;
    /**
     * Block collection ID to identify this block belongs to what
     */
    private volatile long bcId;

    protected Object[] triplets;

    public BlockInfo(short size){
        this.triplets = new Object[3*size];
        this.bcId = -1;
        this.replication = size;
    }
    public BlockInfo(Block block,short size){
        super(block);
        this.triplets = new Object[3*size];
        this.bcId = -1;
        this.replication = size;
    }

    public short getReplication() {
        return replication;
    }

    public void setReplication(short replication) {
        this.replication = replication;
    }

    public long getBlocKCollectionId() {
        return bcId;
    }

    public void setBlocKCollectionId(long bcId) {
        this.bcId = bcId;
    }

    public int getCapacity(){
        return triplets.length/3;
    }

    public Object[] getTriplets() {
        return triplets;
    }

    public void setTriplets(Object[] triplets) {
        this.triplets = triplets;
    }

    public void delete(){
        setBlocKCollectionId(-1);
    }

    public boolean isDeleted(){
        return bcId == -1;
    }

    public DatanodeInfo getDatanodeInfo(int index){
        return (DatanodeInfo) triplets[index*3];
    }

    public BlockInfo getPrevious(int index){
        BlockInfo info = (BlockInfo) triplets[index*3+1];
        return info;
    }

    public BlockInfo getNext(int index){
        BlockInfo info = (BlockInfo) triplets[index*3+2];
        return info;
    }

    public void setDatanodeInfo(int index,DatanodeInfo info){
        triplets[index * 3]  =info;
    }

    public BlockInfo setPrevious(int index,BlockInfo to){
        BlockInfo info = (BlockInfo) triplets[index*3+1];
        triplets[index*3+1] = to;
        return info;
    }

    public BlockInfo setNext(int index, BlockInfo to){
        BlockInfo info = (BlockInfo) triplets[index*3+2];
        triplets[index*3+2] = to;
        return info;
    }

    public int findDatanodeInfo(DatanodeInfo datanodeInfo){
        int len = getCapacity();
        for (int i = 0; i < len; i++) {
            DatanodeInfo cur = getDatanodeInfo(i);
            if (cur == datanodeInfo){
                return i;
            }
        }
        return -1;
    }

    /**
     * insert this block into the head of the list of blocks
     * @param head
     * @param info
     * @return
     */
    public BlockInfo listInsert(BlockInfo head,DatanodeInfo info){
        int dnIndex = this.findDatanodeInfo(info);
        if (dnIndex > 0) return head; // this block already exist
        this.setPrevious(dnIndex,null);
        this.setNext(dnIndex,head);
        if (head != null){
            head.setPrevious(head.findDatanodeInfo(info),this);
        }
        return this;
    }
    public BlockInfo listRemove(BlockInfo head,DatanodeInfo info){
        if(head == null){
            return null;
        }
        int dnIndex = this.findDatanodeInfo(info);
        if (dnIndex < 0){
            return head;
        }
        BlockInfo next = this.getNext(dnIndex);
        BlockInfo prev = this.getPrevious(dnIndex);
        this.setNext(dnIndex,null);
        this.setPrevious(dnIndex,null);
        if (prev != null){
            prev.setNext(prev.findDatanodeInfo(info),next);
        }
        if (next != null){
            next.setPrevious(next.findDatanodeInfo(info),prev);
        }
        if (this == head){
            head = next;
        }
        return head;
    }
}
