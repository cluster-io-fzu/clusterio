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
    /**
     * Datanode which store this block
     */
    private DatanodeInfo info;

    public BlockInfo(Block block){
        super(block);
    }

    public BlockInfo(short size){
        this.bcId = -1;
        this.replication = size;
    }

    public BlockInfo(Block block,short size){
        super(block);
        this.bcId = -1;
        this.replication = size;
    }

    public short getReplication() {
        return replication;
    }

    public void setReplication(short replication) {
        this.replication = replication;
    }

    public long getBlockCollectionId() {
        return bcId;
    }

    public void setBlockCollectionId(long bcId) {
        this.bcId = bcId;
    }

    public DatanodeInfo getDatanodeInfo() {
        return info;
    }

    public void setInfo(DatanodeInfo info) {
        this.info = info;
    }
}
