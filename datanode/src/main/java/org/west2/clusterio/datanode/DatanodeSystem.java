package org.west2.clusterio.datanode;

import org.west2.clusterio.datanode.protocol.DatanodeID;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;
import org.west2.clusterio.datanode.protocol.StorageReport;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the base class of the datanode, every init and
 * specific instances will be stored and got at here (current idea)
 */

public class DatanodeSystem {
    private static final DatanodeSystem sys = new DatanodeSystem();
    private DatanodeID id;
    private DatanodeInfo info;
    private DatanodeRegistration reg;

    private DatanodeSystem() {}

    //TODO Config init
    public DatanodeSystem(DatanodeID id, DatanodeInfo info, DatanodeRegistration reg) {
        this.id = id;
        this.info = info;
        this.reg = reg;
    }

    public List<StorageReport> getStorageReport(){
        ArrayList<StorageReport> reports = new ArrayList<>();
        //TODO blockPool parameter should be handled later
        StorageReport report = new StorageReport(id.getDatanodeUuid(), isAlive(), info.getCapacity(),
                info.getDfsUsed(), info.getRemaining(), info.getDfsUsed());
        reports.add(report);
        return reports;
    }

    public boolean isAlive(){
        return true;
    }

    public static DatanodeSystem getSystem(){
        return sys;
    }

    public DatanodeID getId() {
        return id;
    }

    public void setId(DatanodeID id) {
        this.id = id;
    }

    public DatanodeInfo getInfo() {
        return info;
    }

    public void setInfo(DatanodeInfo info) {
        this.info = info;
    }

    public DatanodeRegistration getReg() {
        return reg;
    }

    public void setReg(DatanodeRegistration reg) {
        this.reg = reg;
    }
}
