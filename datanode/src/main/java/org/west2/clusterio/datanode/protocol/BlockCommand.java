package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocol.Block;

public class BlockCommand extends DatanodeCommand{

    private String poolId;
    private final Block[] blocks;
    private final DatanodeInfo[] targets;
    private final String[] targetStorageIDs;

    public BlockCommand(int action, String poolId, Block[] blocks,
                        DatanodeInfo[] targets, String[] targetStorageIDs) {
        super(action);
        this.poolId = poolId;
        this.blocks = blocks;
        this.targets = targets;
        this.targetStorageIDs = targetStorageIDs;
    }

    private static final DatanodeInfo[] EMPTY_TARGET_DATANODES = {};
    private static final String[] EMPTY_TARGET_STORAGEIDS = {};
    public BlockCommand(int action, String poolId,Block[] blocks) {
        this(action,poolId,blocks,EMPTY_TARGET_DATANODES,EMPTY_TARGET_STORAGEIDS);
    }

    public String getPoolId() {
        return poolId;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public DatanodeInfo[] getTargets() {
        return targets;
    }

    public String[] getTargetStorageIDs() {
        return targetStorageIDs;
    }
}
