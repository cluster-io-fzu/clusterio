package org.west2.clusterio.datanode.protocol;

public class BlockReportContext {
    private int totalPieces;
    private int curRpc;
    private long id;

    public BlockReportContext(int totalPieces, int curRpc, long id) {
        this.totalPieces = totalPieces;
        this.curRpc = curRpc;
        this.id = id;
    }

    public int getTotalPieces() {
        return totalPieces;
    }

    public int getCurRpc() {
        return curRpc;
    }

    public long getId() {
        return id;
    }
}
