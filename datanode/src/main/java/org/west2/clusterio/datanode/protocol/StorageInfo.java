package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.HdfsProtos;

public class StorageInfo {
    private String storageUuid;
    private int layoutVersion;
    private long namespaceID;
    private String clusterID;
    private long cTime;

    public StorageInfo(String storageUuid, int layoutVersion, long namespaceID, String clusterID, long cTime) {
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

    public String getStorageUuid() {
        return storageUuid;
    }

    public int getLayoutVersion() {
        return layoutVersion;
    }

    public long getNamespaceID() {
        return namespaceID;
    }

    public String getClusterID() {
        return clusterID;
    }

    public long getcTime() {
        return cTime;
    }
}
