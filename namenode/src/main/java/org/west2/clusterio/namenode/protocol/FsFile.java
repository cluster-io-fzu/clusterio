package org.west2.clusterio.namenode.protocol;

import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.utils.FileUtil;

public class FsFile implements BlockCollection{
    private int numBlocks;
    private int bcId;
    private long fileLength;
    private String filename;
    private String suffix;
    private Block[] blocks;
    private Block lastBlock;
    private long lastModifiedTime;


    public FsFile(FsFile from){
        this(from.numBlocks,from.bcId,from.fileLength,from.filename,
                from.suffix, from.blocks, from.lastBlock,from.lastModifiedTime);
    }

    public FsFile(int numBlocks, int bcId, long fileLength, String filename, Block[] blocks, Block lastBlock) {
        this(numBlocks,bcId,fileLength,filename, FileUtil.suffix(filename),
                blocks,lastBlock,System.currentTimeMillis());
    }

    public FsFile(int numBlocks, int bcId, long fileLength,
                  String filename, String suffix, Block[] blocks,
                  Block lastBlock, long lastModifiedTime) {
        this.numBlocks = numBlocks;
        this.bcId = bcId;
        this.fileLength = fileLength;
        this.filename = filename;
        this.suffix = suffix;
        this.blocks = blocks;
        this.lastBlock = lastBlock;
        this.lastModifiedTime =  lastModifiedTime;
    }

    public void setBlocks(Block[] blks){
        blocks = blks;
    }

    @Override
    public int numBlocks() {
        return numBlocks;
    }

    public Block getBlock(int index){
        return blocks[index];
    }

    @Override
    public Block[] getBlocks() {
        return blocks;
    }

    public Block getLastBlock(){
        return lastBlock;
    }

    @Override
    public void setBlock(int index,Block blk) {
        blocks[index] = blk;
    }

    @Override
    public long getId() {
        return bcId;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public int getBcId() {
        return bcId;
    }

    public long getFileLength() {
        return fileLength;
    }

    public String getFilename() {
        return filename;
    }

    public String getSuffix() {
        return suffix;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }
}
