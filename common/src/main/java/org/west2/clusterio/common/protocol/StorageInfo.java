package org.west2.clusterio.common.protocol;

import org.west2.clusterio.common.protocolPB.HdfsProtos;

public class StorageInfo {
    private String storageUuid;
    private int layoutVersion;
    private int namespaceID;
    private String clusterID;
    private long cTime;

    public StorageInfo(String storageUuid, int layoutVersion, int namespaceID, String clusterID, int cTime) {
        this.storageUuid = storageUuid;
        this.layoutVersion = layoutVersion;
        this.namespaceID = namespaceID;
        this.clusterID = clusterID;
        this.cTime = cTime;
    }
    public StorageInfo(HdfsProtos.StorageInfoProtoc protoc){
        this.storageUuid = protoc.getStorageUuid();
        this.layoutVersion = protoc.getLayoutVersion();
        this.namespaceID = protoc.getNamespaceID();
        this.clusterID = protoc.getClusterID();
        this.cTime = protoc.getCTime();
    }
    public HdfsProtos.StorageInfoProtoc parse() {
        return HdfsProtos.StorageInfoProtoc.newBuilder()
                .setStorageUuid(this.storageUuid)
                .setNamespaceID(this.namespaceID)
                .setClusterID(this.clusterID)
                .setLayoutVersion(this.layoutVersion)
                .setCTime(this.cTime).build();
    }
}
