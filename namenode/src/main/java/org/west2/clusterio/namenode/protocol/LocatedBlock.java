package org.west2.clusterio.namenode.protocol;

import com.google.common.collect.Lists;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;

import java.util.List;

public class LocatedBlock {
    private long offset;
    private final String[] storageIDs;
    private DatanodeInfo[] cachedLocs;
    private boolean corrupt;
    private final Block b;
    static final DatanodeInfo[] EMPTY_LOCS = new DatanodeInfo[0];
    public LocatedBlock(Block b,DatanodeInfo[] locs){
        this(b,locs,null);
    }
    public LocatedBlock(Block b,DatanodeInfo[] locs,String[] storageIDs){
        this(b,locs,storageIDs,-1,false);
    }
    public LocatedBlock(Block b,DatanodeInfo[] locs,String[] storageIDs,long startOffset,boolean corrupt){
        this.b= b;
        this.offset = startOffset;
        this.corrupt = corrupt;
        this.storageIDs = storageIDs;
        this.cachedLocs = locs==null? EMPTY_LOCS:locs;
    }

    public void updateCachedStorageInfo(){
        if (storageIDs != null){
            for (int i = 0; i < cachedLocs.length; i++) {
                storageIDs[i] = cachedLocs[i].getDatanodeUuid();//Temporary
            }
        }
    }

    public void addCachedLoc(DatanodeInfo loc){
        List<DatanodeInfo> cachedList = Lists.newArrayList(cachedLocs);
        if (cachedList.contains(loc)){
            return;
        }
        cachedList.add(loc);
        cachedLocs = cachedList.toArray(cachedLocs);
    }

    public long getBlockSize(){
        return b.getNumBytes();
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String[] getStorageIDs() {
        return storageIDs;
    }

    public DatanodeInfo[] getCachedLocs() {
        return cachedLocs;
    }

    public void setCachedLocs(DatanodeInfo[] cachedLocs) {
        this.cachedLocs = cachedLocs;
    }

    public boolean isCorrupt() {
        return corrupt;
    }

    public void setCorrupt(boolean corrupt) {
        this.corrupt = corrupt;
    }

    public Block getBlock() {
        return b;
    }
}
