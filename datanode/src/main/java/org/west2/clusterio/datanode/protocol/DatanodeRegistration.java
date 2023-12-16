package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeRegistrationProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos;

public class DatanodeRegistration extends DatanodeID {
    private final StorageInfo storageInfo;
    public DatanodeRegistration(DatanodeID from, StorageInfo storageInfo) {
        super(from);
        this.storageInfo = storageInfo;
    }
    public DatanodeRegistrationProto parseDatanodeReg(){
        HdfsProtos.DatanodeIDProto datanodeIDProto = super.parseDatanodeID();
        HdfsProtos.StorageInfoProtoc infoProtoc = PBHelper.convert(storageInfo);
        return DatanodeProtocol.DatanodeRegistrationProto.newBuilder()
                .setDatanodeId(datanodeIDProto)
                .setStorageInfo(infoProtoc).build();
    }

    public StorageInfo getStorageInfo() {
        return storageInfo;
    }
}
