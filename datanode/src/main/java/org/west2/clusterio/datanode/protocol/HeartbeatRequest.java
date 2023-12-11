package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.HdfsProtos;

import java.util.ArrayList;
import java.util.List;

public class HeartbeatRequest {
    private DatanodeRegistration reg;
    private List<StorageReport> reports;

    public HeartbeatRequest(DatanodeRegistration reg, List<StorageReport> reports) {
        this.reg = reg;
        this.reports = reports;
    }

    public DatanodeProtocol.HeartbeatRequestProto parse(){
        DatanodeProtocol.HeartbeatRequestProto.Builder builder
                = DatanodeProtocol.HeartbeatRequestProto.newBuilder();
        builder.setRegistration(PBHelper.convert(reg));
        for (int i = 0;i < reports.size();i++){
            builder.addReports(reports.get(i).parse());
        }
        return builder.build();
    }

    public DatanodeRegistration getReg() {
        return reg;
    }

    public void setReg(DatanodeRegistration reg) {
        this.reg = reg;
    }

    public List<StorageReport> getReports() {
        return reports;
    }

    public void setReports(List<StorageReport> reports) {
        this.reports = reports;
    }
}
