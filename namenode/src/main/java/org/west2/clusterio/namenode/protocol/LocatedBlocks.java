package org.west2.clusterio.namenode.protocol;

import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LocatedBlocks {
    private final long fileLength;
    private final List<LocatedBlock> blocks;
    private final LocatedBlock lastLocatedBlock;
    private final boolean isLastBlockComplete;
    public LocatedBlocks(){
        fileLength = 0;
        blocks = null;
        lastLocatedBlock = null;
        isLastBlockComplete = false;
    }

    public LocatedBlocks(long fileLength, List<LocatedBlock> blocks,
                         LocatedBlock lastLocatedBlock, boolean isLastBlockComplete) {
        this.fileLength = fileLength;
        this.blocks = blocks;
        this.lastLocatedBlock = lastLocatedBlock;
        this.isLastBlockComplete = isLastBlockComplete;
    }

    public long getFileLength() {
        return fileLength;
    }

    public List<LocatedBlock> getBlocks() {
        return blocks;
    }

    public LocatedBlock getLastLocatedBlock() {
        return lastLocatedBlock;
    }

    public boolean isLastBlockComplete() {
        return isLastBlockComplete;
    }

    public int findBlock(long offset){
        LocatedBlock key = new LocatedBlock(new Block(),DatanodeInfo.EMPTY_ARRAY);
        key.setOffset(offset);
        key.getBlock().setNumBytes(1);
        Comparator<LocatedBlock> comp =
                (a, b) -> {
                    long aBeg = a.getOffset();
                    long bBeg = b.getOffset();
                    long aEnd = aBeg + a.getBlockSize();
                    long bEnd = bBeg + b.getBlockSize();
                    if(aBeg <= bBeg && bEnd <= aEnd
                            || bBeg <= aBeg && aEnd <= bEnd)
                        return 0; // one of the blocks is inside the other
                    if(aBeg < bBeg)
                        return -1; // a's left bound is to the left of the b's
                    return 1;
                };
        return Collections.binarySearch(blocks,key,comp);
    }
}
