package org.west2.clusterio.namenode.pojo;

public class DatanodeInfo extends DatanodeID{
    private long capacity;
    private long dfsUsed;
    private long nonDfsUsed;
    private long remaining;
    private long lastUpdated;
    private int numBlocks;
    private long lastBlockReportTime;

    public DatanodeInfo(String datanodeUuid, String ipAddr, String hostName, int port, long capacity, long dfsUsed, long nonDfsUsed, long remaining, long lastUpdated, int numBlocks, long lastBlockReportTime) {
        super(datanodeUuid, ipAddr, hostName, port);
        this.capacity = capacity;
        this.dfsUsed = dfsUsed;
        this.nonDfsUsed = nonDfsUsed;
        this.remaining = remaining;
        this.lastUpdated = lastUpdated;
        this.numBlocks = numBlocks;
        this.lastBlockReportTime = lastBlockReportTime;
    }

    public DatanodeInfo(String datanodeUuid, String ipAddr, String hostName, int port) {
        super(datanodeUuid, ipAddr, hostName, port);
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
}
