package org.west2.clusterio.common.protocol;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Block implements Serializable {
    public static final String BLOCK_FILE_PREFIX = "blk_";
    public static final String METADATA_EXTENSION = ".meta";
    private long blockId;
    private long numBytes;
    private long generationStamp;

    public static final Pattern blockFilePattern
            = Pattern.compile(BLOCK_FILE_PREFIX+"(-??\\d++)$");

    public Block(){
        this(0,0,0);
    }

    public Block(long blockId){
        this(blockId,0,0);
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
