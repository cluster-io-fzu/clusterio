package org.west2.clusterio.common.protocol;

public class Block {
    private long blockId;
    private long numBytes;
    private long generationStamp;

    public Block(){
        this(0,0,0);
    }

    public Block(Block from){
        this(from.blockId,from.numBytes,from.generationStamp);
    }

    public Block(final long blockId,final long numBytes,final long generationStamp) {
        this.blockId = blockId;
        this.numBytes = numBytes;
        this.generationStamp = generationStamp;
    }

    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }

    public long getNumBytes() {
        return numBytes;
    }

    public void setNumBytes(long numBytes) {
        this.numBytes = numBytes;
    }

    public long getGenerationStamp() {
        return generationStamp;
    }

    public void setGenerationStamp(long generationStamp) {
        this.generationStamp = generationStamp;
    }
}
