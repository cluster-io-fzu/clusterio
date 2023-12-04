package org.west2.clusterio.common.protocol;

import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.HdfsProtos;

public class DatanodeRegistration extends DatanodeID {
    private final StorageInfo storageInfo;
    public DatanodeRegistration(DatanodeID from, StorageInfo storageInfo) {
        super(from);
        this.storageInfo = storageInfo;
    }
    public DatanodeRegistration(DatanodeProtocol.DatanodeRegistrationProto proto){
        super(proto.getDatanodeId());
        this.storageInfo = new StorageInfo(proto.getStorageInfo());
    }
    public DatanodeProtocol.DatanodeRegistrationProto parseDatanodeReg(){
        HdfsProtos.DatanodeIDProto datanodeIDProto = super.parseDatanodeID();
        HdfsProtos.StorageInfoProtoc infoProtoc = storageInfo.parse();
        return DatanodeProtocol.DatanodeRegistrationProto.newBuilder()
                .setDatanodeId(datanodeIDProto)
                .setStorageInfo(infoProtoc).build();
    }
}
