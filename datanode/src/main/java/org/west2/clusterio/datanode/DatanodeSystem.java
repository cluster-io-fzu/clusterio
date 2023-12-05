package org.west2.clusterio.datanode;

import org.west2.clusterio.datanode.protocol.DatanodeID;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;

public class DatanodeSystem {
    private static DatanodeSystem sys = new DatanodeSystem();
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
