package org.west2.clusterio.datanode.protocol;

public class BlockReportRequest {
    private DatanodeRegistration reg;
    private String blockPoolId;
    private StorageBlockReport[] reports;
    private BlockReportContext context;

    public BlockReportRequest(DatanodeRegistration reg, String blockPoolId, StorageBlockReport[] reports, BlockReportContext context) {
        this.reg = reg;
        this.blockPoolId = blockPoolId;
        this.reports = reports;
        this.context = context;
    }

    public DatanodeRegistration getReg() {
        return reg;
    }

    public String getBlockPoolId() {
        return blockPoolId;
    }

    public StorageBlockReport[] getReports() {
        return reports;
    }

    public BlockReportContext getContext() {
        return context;
    }
}
