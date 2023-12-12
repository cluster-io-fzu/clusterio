package org.west2.clusterio.datanode.protocol;

public class DatanodeInfo extends DatanodeID {
    private long capacity;
    private long dfsUsed;
    private long nonDfsUsed; //haven't been used
    private long remaining;
    private long lastUpdated;
    private int numBlocks;
    private long lastBlockReportTime;
    private DatanodeStatus status;giyt
    public static final DatanodeInfo[] EMPTY_ARRAY = {};
    public DatanodeInfo(DatanodeID id){
        super(id);
        setLastUpdated(System.currentTimeMillis());
    }

    public DatanodeInfo(DatanodeID id, long capacity, long dfsUsed, long nonDfsUsed, long remaining, long lastUpdated, int numBlocks, long lastBlockReportTime) {
        super(id);
        this.capacity = capacity;
        this.dfsUsed = dfsUsed;
        this.nonDfsUsed = nonDfsUsed;
        this.remaining = remaining;
        this.lastUpdated = lastUpdated;
        this.numBlocks = numBlocks;
        this.lastBlockReportTime = lastBlockReportTime;
        setLastUpdated(System.currentTimeMillis());
    }
    //synchronized method, avoid thread problem
    public void update(DatanodeInfo from){
        synchronized (this){
            this.capacity = from.capacity;
            this.dfsUsed = from.dfsUsed;
            this.nonDfsUsed = from.nonDfsUsed;
            this.remaining = from.remaining;
            this.numBlocks = from.numBlocks;
            this.lastUpdated = System.currentTimeMillis();
            this.status = DatanodeStatus.ACTIVE;
        }
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getDfsUsed() {
        return dfsUsed;
    }

    public void setDfsUsed(long dfsUsed) {
        this.dfsUsed = dfsUsed;
    }

    public long getNonDfsUsed() {
        return nonDfsUsed;
    }

    public void setNonDfsUsed(long nonDfsUsed) {
        this.nonDfsUsed = nonDfsUsed;
    }

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }

    public long getLastBlockReportTime() {
        return lastBlockReportTime;
    }

    public void setLastBlockReportTime(long lastBlockReportTime) {
        this.lastBlockReportTime = lastBlockReportTime;
    }

    public DatanodeStatus getStatus() {
        return status;
    }

    public void setStatus(DatanodeStatus status) {
        this.status = status;
    }
}
